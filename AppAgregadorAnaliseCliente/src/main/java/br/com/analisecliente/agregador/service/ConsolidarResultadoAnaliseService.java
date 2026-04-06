package br.com.analisecliente.agregador.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.enums.StatusAnalise;
import br.com.analisecliente.agregador.model.ErroAnalise;
import br.com.analisecliente.agregador.model.ProcessoAnalise;
import br.com.analisecliente.agregador.model.ResultadoAnalise;
import br.com.analisecliente.agregador.repository.ProcessoAnaliseRepository;

@Service
public class ConsolidarResultadoAnaliseService {

    private static final long TIMEOUT_SECONDS = 3L;
    private static final String ORIGEM_ERRO = "AgregadorAnaliseCliente";

    private final ProcessoAnaliseRepository processoAnaliseRepository;

    public ConsolidarResultadoAnaliseService(ProcessoAnaliseRepository processoAnaliseRepository) {
        this.processoAnaliseRepository = processoAnaliseRepository;
    }

    public void executar(String requestId) {
        ProcessoAnalise processoAnalise = processoAnaliseRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException(
                        "Processo não encontrado para requestId=" + requestId));

        if (processoJaFinalizado(processoAnalise)) {
            return;
        }

        ResultadoAnalise resultadoAnalise = processoAnalise.getResultado();

        if (possuiTodosOsResultados(resultadoAnalise)) {
            concluirComSucesso(processoAnalise, resultadoAnalise);
            return;
        }

        if (timeoutExpirado(processoAnalise)) {
            concluirComErroPorTimeout(processoAnalise, resultadoAnalise);
            return;
        }

        processoAnalise.setStatus(StatusAnalise.PROCESSANDO.name());
        processoAnalise.setDataHoraAtualizacao(LocalDateTime.now());
        processoAnaliseRepository.save(processoAnalise);
    }

    private boolean processoJaFinalizado(ProcessoAnalise processoAnalise) {
        return StatusAnalise.CONCLUIDO.name().equals(processoAnalise.getStatus())
                || StatusAnalise.ERRO.name().equals(processoAnalise.getStatus());
    }

    private boolean possuiTodosOsResultados(ResultadoAnalise resultadoAnalise) {
        return resultadoAnalise != null
                && resultadoAnalise.getVerificacaoCredito() != null
                && resultadoAnalise.getVerificacaoFichaLimpa() != null
                && resultadoAnalise.getVerificacaoStatusCliente() != null;
    }

    private boolean timeoutExpirado(ProcessoAnalise processoAnalise) {
        if (processoAnalise.getDataHoraCriacao() == null) {
            return false;
        }

        return Duration.between(
                processoAnalise.getDataHoraCriacao(),
                LocalDateTime.now()
        ).toMillis() >= TIMEOUT_SECONDS * 1000;
    }

    private void concluirComSucesso(ProcessoAnalise processoAnalise, ResultadoAnalise resultadoAnalise) {
        boolean possuiCredito = obterBoolean(resultadoAnalise.getVerificacaoCredito(), "possuiCredito");
        boolean possuiFichaLimpa = obterBoolean(resultadoAnalise.getVerificacaoFichaLimpa(), "possuiFichaLimpa");
        boolean clienteAtivo = obterBoolean(resultadoAnalise.getVerificacaoStatusCliente(), "clienteAtivo");

        boolean aprovado = possuiCredito && possuiFichaLimpa && clienteAtivo;

        Map<String, Object> resultadoConsolidado = new HashMap<String, Object>();
        resultadoConsolidado.put("aprovado", Boolean.valueOf(aprovado));
        resultadoConsolidado.put("motivo", aprovado ? "Cliente elegível" : "Cliente inelegível");

        resultadoAnalise.setResultadoConsolidado(resultadoConsolidado);

        processoAnalise.setStatus(StatusAnalise.CONCLUIDO.name());
        processoAnalise.setErro(null);
        processoAnalise.setDataHoraAtualizacao(LocalDateTime.now());

        processoAnaliseRepository.save(processoAnalise);

        System.out.println("Resultado consolidado gravado no Mongo. requestId=" + processoAnalise.getRequestId());
    }

    private void concluirComErroPorTimeout(ProcessoAnalise processoAnalise, ResultadoAnalise resultadoAnalise) {
        List<String> pendencias = identificarPendencias(resultadoAnalise);

        if (resultadoAnalise != null) {
            resultadoAnalise.setResultadoConsolidado(null);
        }

        ErroAnalise erro = new ErroAnalise();
        erro.setOrigem(ORIGEM_ERRO);
        erro.setMensagem("Timeout aguardando retorno de: " + String.join(", ", pendencias));

        processoAnalise.setStatus(StatusAnalise.ERRO.name());
        processoAnalise.setErro(erro);
        processoAnalise.setDataHoraAtualizacao(LocalDateTime.now());

        processoAnaliseRepository.save(processoAnalise);

        System.out.println("Processo encerrado com erro por timeout. requestId=" + processoAnalise.getRequestId());
    }

    private List<String> identificarPendencias(ResultadoAnalise resultadoAnalise) {
        List<String> pendencias = new ArrayList<String>();

        if (resultadoAnalise == null) {
            pendencias.add("crédito");
            pendencias.add("ficha limpa");
            pendencias.add("status cliente");
            return pendencias;
        }

        if (resultadoAnalise.getVerificacaoCredito() == null) {
            pendencias.add("crédito");
        }

        if (resultadoAnalise.getVerificacaoFichaLimpa() == null) {
            pendencias.add("ficha limpa");
        }

        if (resultadoAnalise.getVerificacaoStatusCliente() == null) {
            pendencias.add("status cliente");
        }

        return pendencias;
    }

    private boolean obterBoolean(Map<String, Object> mapa, String chave) {
        if (mapa == null) {
            return false;
        }

        Object valor = mapa.get(chave);

        if (valor instanceof Boolean) {
            return ((Boolean) valor).booleanValue();
        }

        if (valor instanceof String) {
            return Boolean.parseBoolean((String) valor);
        }

        return false;
    }
}