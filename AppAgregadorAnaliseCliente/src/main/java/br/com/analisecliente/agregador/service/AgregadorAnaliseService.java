package br.com.analisecliente.agregador.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.client.CreditoClient;
import br.com.analisecliente.agregador.client.FichaLimpaClient;
import br.com.analisecliente.agregador.client.StatusClienteClient;
import br.com.analisecliente.agregador.dto.ProcessarAnaliseRequest;
import br.com.analisecliente.agregador.dto.ResponsePadrao;
import br.com.analisecliente.agregador.dto.ResultadoAgregadoResponse;
import br.com.analisecliente.agregador.dto.VerificacaoCreditoDados;
import br.com.analisecliente.agregador.dto.VerificacaoFichaLimpaDados;
import br.com.analisecliente.agregador.dto.VerificacaoStatusClienteDados;
import br.com.analisecliente.agregador.enums.StatusAnalise;
import br.com.analisecliente.agregador.exception.ProcessoNaoEncontradoException;
import br.com.analisecliente.agregador.exception.RequisicaoInvalidaException;
import br.com.analisecliente.agregador.model.ErroAnalise;
import br.com.analisecliente.agregador.model.ProcessoAnalise;
import br.com.analisecliente.agregador.model.ResultadoAnalise;
import br.com.analisecliente.agregador.repository.ProcessoAnaliseRepository;
import br.com.analisecliente.agregador.util.ResponseFactory;

@Service
public class AgregadorAnaliseService {

    private static final long TIMEOUT_SECONDS = 3L;
    private static final String ORIGEM_ERRO = "AgregadorAnaliseCliente";

    private final CreditoClient creditoClient;
    private final FichaLimpaClient fichaLimpaClient;
    private final StatusClienteClient statusClienteClient;
    private final ProcessoAnaliseRepository processoAnaliseRepository;
    private final Executor agregadorExecutor;

    public AgregadorAnaliseService(CreditoClient creditoClient,
                                   FichaLimpaClient fichaLimpaClient,
                                   StatusClienteClient statusClienteClient,
                                   ProcessoAnaliseRepository processoAnaliseRepository,
                                   @Qualifier("agregadorExecutor") Executor agregadorExecutor) {
        this.creditoClient = creditoClient;
        this.fichaLimpaClient = fichaLimpaClient;
        this.statusClienteClient = statusClienteClient;
        this.processoAnaliseRepository = processoAnaliseRepository;
        this.agregadorExecutor = agregadorExecutor;
    }

    public ResponsePadrao processar(ProcessarAnaliseRequest request) {
        validarRequest(request);

        String cpfNumerico = normalizarCpf(request.getCpf());
        request.setCpf(cpfNumerico);

        ProcessoAnalise processo = processoAnaliseRepository.findById(request.getRequestId())
                .orElseThrow(() -> new ProcessoNaoEncontradoException(
                        "Processo não encontrado para o requestId informado"));

        try {
            CompletableFuture<VerificacaoCreditoDados> creditoFuture =
                    executarComTimeout("Crédito", new CheckedSupplier<VerificacaoCreditoDados>() {
                        @Override
                        public VerificacaoCreditoDados get() throws Exception {
                            return creditoClient.verificar(cpfNumerico);
                        }
                    });

            CompletableFuture<VerificacaoFichaLimpaDados> fichaLimpaFuture =
                    executarComTimeout("Ficha Limpa", new CheckedSupplier<VerificacaoFichaLimpaDados>() {
                        @Override
                        public VerificacaoFichaLimpaDados get() throws Exception {
                            return fichaLimpaClient.verificar(cpfNumerico);
                        }
                    });

            CompletableFuture<VerificacaoStatusClienteDados> statusClienteFuture =
                    executarComTimeout("Status Cliente", new CheckedSupplier<VerificacaoStatusClienteDados>() {
                        @Override
                        public VerificacaoStatusClienteDados get() throws Exception {
                            return statusClienteClient.verificar(cpfNumerico);
                        }
                    });

            CompletableFuture.allOf(creditoFuture, fichaLimpaFuture, statusClienteFuture).join();

            VerificacaoCreditoDados credito = creditoFuture.join();
            VerificacaoFichaLimpaDados fichaLimpa = fichaLimpaFuture.join();
            VerificacaoStatusClienteDados statusCliente = statusClienteFuture.join();

            boolean aprovado = Boolean.TRUE.equals(credito.getPossuiCredito())
                    && Boolean.TRUE.equals(fichaLimpa.getPossuiFichaLimpa())
                    && Boolean.TRUE.equals(statusCliente.getClienteAtivo());

            ResultadoAgregadoResponse dados = new ResultadoAgregadoResponse();
            dados.setRequestId(request.getRequestId());
            dados.setCpf(cpfNumerico);
            dados.setVerificacaoCredito(credito);
            dados.setVerificacaoFichaLimpa(fichaLimpa);
            dados.setVerificacaoStatusCliente(statusCliente);
            dados.setAprovado(aprovado);
            dados.setMotivo(aprovado ? "Cliente elegível" : "Cliente inelegível");

            atualizarProcessoComSucesso(processo, credito, fichaLimpa, statusCliente, aprovado);
            processoAnaliseRepository.save(processo);

            return ResponseFactory.sucesso(dados, "Processamento do agregador realizado com sucesso");

        } catch (Exception ex) {
            RuntimeException excecaoTratada = unwrapException(ex);
            atualizarProcessoComErro(processo, excecaoTratada);
            processoAnaliseRepository.save(processo);
            throw excecaoTratada;
        }
    }

    private <T> CompletableFuture<T> executarComTimeout(String nomeServico, CheckedSupplier<T> supplier) {
        return CompletableFuture
                .supplyAsync(() -> {
                    try {
                        return supplier.get();
                    } catch (Exception ex) {
                        throw new CompletionException(
                                new RuntimeException("Erro ao consultar serviço " + nomeServico + ": " + ex.getMessage(), ex));
                    }
                }, agregadorExecutor)
                .orTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .handle((resultado, ex) -> {
                    if (ex == null) {
                        return resultado;
                    }

                    Throwable causa = ex;

                    if (causa instanceof CompletionException && causa.getCause() != null) {
                        causa = causa.getCause();
                    }

                    if (causa instanceof TimeoutException) {
                        throw new CompletionException(
                                new RuntimeException(
                                        "Timeout ao consultar serviço " + nomeServico + " após "
                                                + TIMEOUT_SECONDS + " segundos",
                                        causa));
                    }

                    if (causa instanceof RuntimeException) {
                        throw (RuntimeException) causa;
                    }

                    throw new CompletionException(
                            new RuntimeException("Erro ao consultar serviço " + nomeServico + ": " + causa.getMessage(), causa));
                });
    }

    private RuntimeException unwrapException(Throwable ex) {
        Throwable causa = ex;

        while (causa instanceof CompletionException && causa.getCause() != null) {
            causa = causa.getCause();
        }

        if (causa instanceof RuntimeException) {
            return (RuntimeException) causa;
        }

        return new RuntimeException(causa.getMessage(), causa);
    }

    private void atualizarProcessoComSucesso(ProcessoAnalise processo,
                                             VerificacaoCreditoDados credito,
                                             VerificacaoFichaLimpaDados fichaLimpa,
                                             VerificacaoStatusClienteDados statusCliente,
                                             boolean aprovado) {

        ResultadoAnalise resultado = new ResultadoAnalise();

        Map<String, Object> verificacaoCredito = new HashMap<String, Object>();
        verificacaoCredito.put("possuiCredito", credito.getPossuiCredito());
        verificacaoCredito.put("mensagem", credito.getMensagem());

        Map<String, Object> verificacaoFichaLimpa = new HashMap<String, Object>();
        verificacaoFichaLimpa.put("possuiFichaLimpa", fichaLimpa.getPossuiFichaLimpa());
        verificacaoFichaLimpa.put("mensagem", fichaLimpa.getMensagem());

        Map<String, Object> verificacaoStatusCliente = new HashMap<String, Object>();
        verificacaoStatusCliente.put("clienteAtivo", statusCliente.getClienteAtivo());
        verificacaoStatusCliente.put("mensagem", statusCliente.getMensagem());

        Map<String, Object> resultadoConsolidado = new HashMap<String, Object>();
        resultadoConsolidado.put("aprovado", Boolean.valueOf(aprovado));
        resultadoConsolidado.put("motivo", aprovado ? "Cliente elegível" : "Cliente inelegível");

        resultado.setVerificacaoCredito(verificacaoCredito);
        resultado.setVerificacaoFichaLimpa(verificacaoFichaLimpa);
        resultado.setVerificacaoStatusCliente(verificacaoStatusCliente);
        resultado.setResultadoConsolidado(resultadoConsolidado);

        processo.setStatus(StatusAnalise.CONCLUIDO.name());
        processo.setResultado(resultado);
        processo.setErro(null);
        processo.setDataHoraAtualizacao(LocalDateTime.now());
    }

    private void atualizarProcessoComErro(ProcessoAnalise processo, Throwable ex) {
        ErroAnalise erro = new ErroAnalise();
        erro.setOrigem(ORIGEM_ERRO);
        erro.setMensagem(ex.getMessage());

        processo.setStatus(StatusAnalise.ERRO.name());
        processo.setResultado(null);
        processo.setErro(erro);
        processo.setDataHoraAtualizacao(LocalDateTime.now());
    }

    private void validarRequest(ProcessarAnaliseRequest request) {
        if (request == null) {
            throw new RequisicaoInvalidaException("Body da requisição é obrigatório");
        }

        if (request.getRequestId() == null || request.getRequestId().trim().isEmpty()) {
            throw new RequisicaoInvalidaException("requestId é obrigatório");
        }

        if (request.getCpf() == null || request.getCpf().trim().isEmpty()) {
            throw new RequisicaoInvalidaException("CPF é obrigatório");
        }

        String cpfNumerico = normalizarCpf(request.getCpf());

        if (cpfNumerico.length() != 11) {
            throw new RequisicaoInvalidaException("CPF deve conter 11 dígitos");
        }
    }

    private String normalizarCpf(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    @FunctionalInterface
    private interface CheckedSupplier<T> {
        T get() throws Exception;
    }
}