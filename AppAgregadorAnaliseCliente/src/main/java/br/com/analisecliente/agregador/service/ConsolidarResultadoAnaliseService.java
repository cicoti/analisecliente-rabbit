package br.com.analisecliente.agregador.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.enums.StatusAnalise;
import br.com.analisecliente.agregador.model.ProcessoAnalise;
import br.com.analisecliente.agregador.model.ResultadoAnalise;
import br.com.analisecliente.agregador.repository.ProcessoAnaliseRepository;

@Service
public class ConsolidarResultadoAnaliseService {

    private final ProcessoAnaliseRepository processoAnaliseRepository;

    public ConsolidarResultadoAnaliseService(ProcessoAnaliseRepository processoAnaliseRepository) {
        this.processoAnaliseRepository = processoAnaliseRepository;
    }

    public void executar(String requestId) {
        ProcessoAnalise processoAnalise = processoAnaliseRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException(
                        "Processo não encontrado para requestId=" + requestId));

        ResultadoAnalise resultadoAnalise = processoAnalise.getResultado();
        if (resultadoAnalise == null) {
            return;
        }

        if (!possuiTodosOsResultados(resultadoAnalise)) {
            return;
        }

        boolean possuiCredito = obterBoolean(resultadoAnalise.getVerificacaoCredito(), "possuiCredito");
        boolean possuiFichaLimpa = obterBoolean(resultadoAnalise.getVerificacaoFichaLimpa(), "possuiFichaLimpa");
        boolean clienteAtivo = obterBoolean(resultadoAnalise.getVerificacaoStatusCliente(), "clienteAtivo");

        boolean aprovado = possuiCredito && possuiFichaLimpa && clienteAtivo;

        Map<String, Object> resultadoConsolidado = new HashMap<String, Object>();
        resultadoConsolidado.put("aprovado", Boolean.valueOf(aprovado));
        resultadoConsolidado.put("motivo", aprovado ? "Cliente elegível" : "Cliente inelegível");

        resultadoAnalise.setResultadoConsolidado(resultadoConsolidado);

        processoAnalise.setStatus(StatusAnalise.CONCLUIDO.name());
        processoAnalise.setDataHoraAtualizacao(LocalDateTime.now());

        processoAnaliseRepository.save(processoAnalise);

        System.out.println("Resultado consolidado gravado no Mongo. requestId=" + processoAnalise.getRequestId());
    }

    private boolean possuiTodosOsResultados(ResultadoAnalise resultadoAnalise) {
        return resultadoAnalise.getVerificacaoCredito() != null
                && resultadoAnalise.getVerificacaoFichaLimpa() != null
                && resultadoAnalise.getVerificacaoStatusCliente() != null;
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