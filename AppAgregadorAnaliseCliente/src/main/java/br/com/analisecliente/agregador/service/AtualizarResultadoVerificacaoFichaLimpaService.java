package br.com.analisecliente.agregador.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.dto.ResultadoVerificacaoFichaLimpaMessage;
import br.com.analisecliente.agregador.model.ProcessoAnalise;
import br.com.analisecliente.agregador.model.ResultadoAnalise;
import br.com.analisecliente.agregador.repository.ProcessoAnaliseRepository;

@Service
public class AtualizarResultadoVerificacaoFichaLimpaService {

    private final ProcessoAnaliseRepository processoAnaliseRepository;

    public AtualizarResultadoVerificacaoFichaLimpaService(ProcessoAnaliseRepository processoAnaliseRepository) {
        this.processoAnaliseRepository = processoAnaliseRepository;
    }

    public void executar(ResultadoVerificacaoFichaLimpaMessage message) {
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

        Map<String, Object> verificacaoFichaLimpa = new HashMap<String, Object>();
        verificacaoFichaLimpa.put("possuiFichaLimpa", message.getPossuiFichaLimpa());
        verificacaoFichaLimpa.put("mensagem", message.getMensagem());
        verificacaoFichaLimpa.put("dataHoraProcessamento", message.getDataHoraProcessamento());

        resultadoAnalise.setVerificacaoFichaLimpa(verificacaoFichaLimpa);

        processoAnaliseRepository.save(processoAnalise);

        System.out.println("Resultado de ficha limpa gravado no Mongo. requestId=" + message.getRequestId());
    }
}