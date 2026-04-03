package br.com.analisecliente.agregador.service;

import java.time.LocalDateTime;
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

        ProcessoAnalise processo = processoAnaliseRepository.findById(request.getRequestId())
                .orElseThrow(() -> new ProcessoNaoEncontradoException("Processo não encontrado para o requestId informado"));

        try {
            CompletableFuture<VerificacaoCreditoDados> creditoFuture =
                    executarComTimeout("Crédito", () -> creditoClient.verificar(request.getCpf()));

            CompletableFuture<VerificacaoFichaLimpaDados> fichaLimpaFuture =
                    executarComTimeout("Ficha Limpa", () -> fichaLimpaClient.verificar(request.getCpf()));

            CompletableFuture<VerificacaoStatusClienteDados> statusClienteFuture =
                    executarComTimeout("Status Cliente", () -> statusClienteClient.verificar(request.getCpf()));

            CompletableFuture.allOf(creditoFuture, fichaLimpaFuture, statusClienteFuture).join();

            VerificacaoCreditoDados credito = creditoFuture.join();
            VerificacaoFichaLimpaDados fichaLimpa = fichaLimpaFuture.join();
            VerificacaoStatusClienteDados statusCliente = statusClienteFuture.join();

            boolean aprovado = Boolean.TRUE.equals(credito.getPossuiCredito())
                    && Boolean.TRUE.equals(fichaLimpa.getPossuiFichaLimpa())
                    && Boolean.TRUE.equals(statusCliente.getClienteAtivo());

            ResultadoAgregadoResponse dados = new ResultadoAgregadoResponse();
            dados.setRequestId(request.getRequestId());
            dados.setCpf(request.getCpf());
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
                .exceptionally(ex -> {
                    Throwable causa = ex;

                    if (causa instanceof CompletionException && causa.getCause() != null) {
                        causa = causa.getCause();
                    }

                    if (causa instanceof TimeoutException) {
                        throw new CompletionException(
                                new RuntimeException("Timeout ao consultar serviço " + nomeServico + " após "
                                        + TIMEOUT_SECONDS + " segundos", causa));
                    }

                    if (causa instanceof RuntimeException) {
                        throw (RuntimeException) causa;
                    }

                    throw new CompletionException(
                            new RuntimeException("Erro ao consultar serviço " + nomeServico + ": " + causa.getMessage(), causa));
                });
    }

    private RuntimeException unwrapException(Exception ex) {
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
        resultado.setVerificacaoCredito(Map.of(
                "possuiCredito", credito.getPossuiCredito(),
                "mensagem", credito.getMensagem()
        ));
        resultado.setVerificacaoFichaLimpa(Map.of(
                "possuiFichaLimpa", fichaLimpa.getPossuiFichaLimpa(),
                "mensagem", fichaLimpa.getMensagem()
        ));
        resultado.setVerificacaoStatusCliente(Map.of(
                "clienteAtivo", statusCliente.getClienteAtivo(),
                "mensagem", statusCliente.getMensagem()
        ));
        resultado.setResultadoConsolidado(Map.of(
                "aprovado", aprovado,
                "motivo", aprovado ? "Cliente elegível" : "Cliente inelegível"
        ));

        processo.setStatus(StatusAnalise.CONCLUIDO);
        processo.setResultado(resultado);
        processo.setErro(null);
        processo.setDataHoraAtualizacao(LocalDateTime.now());
    }

    private void atualizarProcessoComErro(ProcessoAnalise processo, Throwable ex) {
        ErroAnalise erro = new ErroAnalise();
        erro.setOrigem("AgregadorAnaliseCliente");
        erro.setMensagem(ex.getMessage());

        processo.setStatus(StatusAnalise.ERRO);
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

        String cpfNumerico = request.getCpf().replaceAll("\\D", "");

        if (cpfNumerico.length() != 11) {
            throw new RequisicaoInvalidaException("CPF deve conter 11 dígitos");
        }

        request.setCpf(cpfNumerico);
    }

    @FunctionalInterface
    private interface CheckedSupplier<T> {
        T get() throws Exception;
    }
}