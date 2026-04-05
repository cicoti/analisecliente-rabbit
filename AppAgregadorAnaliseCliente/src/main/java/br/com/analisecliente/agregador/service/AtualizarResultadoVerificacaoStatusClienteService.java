package br.com.analisecliente.agregador.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.dto.ResultadoVerificacaoStatusClienteMessage;
import br.com.analisecliente.agregador.model.ProcessoAnalise;
import br.com.analisecliente.agregador.model.ResultadoAnalise;
import br.com.analisecliente.agregador.repository.ProcessoAnaliseRepository;

@Service
public class AtualizarResultadoVerificacaoStatusClienteService {

    private final ProcessoAnaliseRepository processoAnaliseRepository;
    private final ConsolidarResultadoAnaliseService consolidarResultadoAnaliseService;

    public AtualizarResultadoVerificacaoStatusClienteService(
            ProcessoAnaliseRepository processoAnaliseRepository,
            ConsolidarResultadoAnaliseService consolidarResultadoAnaliseService) {
        this.processoAnaliseRepository = processoAnaliseRepository;
        this.consolidarResultadoAnaliseService = consolidarResultadoAnaliseService;
    }

    public void executar(ResultadoVerificacaoStatusClienteMessage message) {
        ProcessoAnalise processoAnalise = processoAnaliseRepository.findById(message.getRequestId())
                .orElseThrow(() -> new RuntimeException(
                        "Processo não encontrado para requestId=" + message.getRequestId()));

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

        processoAnalise.setDataHoraAtualizacao(LocalDateTime.now());

        processoAnaliseRepository.save(processoAnalise);

        System.out.println("Resultado de status do cliente gravado no Mongo. requestId=" + message.getRequestId());

        consolidarResultadoAnaliseService.executar(message.getRequestId());
    }
}