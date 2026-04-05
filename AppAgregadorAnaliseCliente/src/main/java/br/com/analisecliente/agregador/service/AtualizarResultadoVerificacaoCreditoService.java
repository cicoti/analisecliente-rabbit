package br.com.analisecliente.agregador.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.dto.ResultadoVerificacaoCreditoMessage;
import br.com.analisecliente.agregador.model.ProcessoAnalise;
import br.com.analisecliente.agregador.model.ResultadoAnalise;
import br.com.analisecliente.agregador.repository.ProcessoAnaliseRepository;

@Service
public class AtualizarResultadoVerificacaoCreditoService {

    private final ProcessoAnaliseRepository processoAnaliseRepository;

    public AtualizarResultadoVerificacaoCreditoService(ProcessoAnaliseRepository processoAnaliseRepository) {
        this.processoAnaliseRepository = processoAnaliseRepository;
    }

    public void executar(ResultadoVerificacaoCreditoMessage message) {
        Optional<ProcessoAnalise> processoOptional = processoAnaliseRepository.findByRequestId(message.getRequestId());

        if (!processoOptional.isPresent()) {
            System.out.println("Processo não encontrado para requestId=" + message.getRequestId());
            return;
        }

        ProcessoAnalise processoAnalise = processoOptional.get();

        ResultadoAnalise resultadoAnalise = processoAnalise.getResultado();
        if (resultadoAnalise == null) {
            resultadoAnalise = new ResultadoAnalise();
            processoAnalise.setResultado(resultadoAnalise);
        }

        Map<String, Object> verificacaoCredito = new HashMap<String, Object>();
        verificacaoCredito.put("possuiCredito", message.getPossuiCredito());
        verificacaoCredito.put("mensagem", message.getMensagem());
        verificacaoCredito.put("dataHoraProcessamento", message.getDataHoraProcessamento());

        resultadoAnalise.setVerificacaoCredito(verificacaoCredito);

        processoAnaliseRepository.save(processoAnalise);

        System.out.println("Resultado de crédito gravado no Mongo. requestId=" + message.getRequestId());
    }
}