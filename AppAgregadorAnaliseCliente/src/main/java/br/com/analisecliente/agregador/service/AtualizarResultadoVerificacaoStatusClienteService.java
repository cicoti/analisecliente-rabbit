package br.com.analisecliente.agregador.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.dto.ResultadoVerificacaoStatusClienteMessage;
import br.com.analisecliente.agregador.model.ProcessoAnalise;
import br.com.analisecliente.agregador.model.ResultadoAnalise;
import br.com.analisecliente.agregador.repository.ProcessoAnaliseRepository;

@Service
public class AtualizarResultadoVerificacaoStatusClienteService {

    private final ProcessoAnaliseRepository processoAnaliseRepository;

    public AtualizarResultadoVerificacaoStatusClienteService(ProcessoAnaliseRepository processoAnaliseRepository) {
        this.processoAnaliseRepository = processoAnaliseRepository;
    }

    public void executar(ResultadoVerificacaoStatusClienteMessage message) {
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

        Map<String, Object> verificacaoStatusCliente = new HashMap<String, Object>();
        verificacaoStatusCliente.put("clienteAtivo", message.getClienteAtivo());
        verificacaoStatusCliente.put("mensagem", message.getMensagem());
        verificacaoStatusCliente.put("dataHoraProcessamento", message.getDataHoraProcessamento());

        resultadoAnalise.setVerificacaoStatusCliente(verificacaoStatusCliente);

        processoAnaliseRepository.save(processoAnalise);

        System.out.println("Resultado de status do cliente gravado no Mongo. requestId=" + message.getRequestId());
    }
}