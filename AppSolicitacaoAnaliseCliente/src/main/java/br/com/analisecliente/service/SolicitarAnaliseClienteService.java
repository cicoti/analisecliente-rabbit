package br.com.analisecliente.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.analisecliente.client.AgregadorClient;
import br.com.analisecliente.dto.ResponsePadrao;
import br.com.analisecliente.dto.SolicitarAnaliseClienteRequest;
import br.com.analisecliente.enums.StatusAnalise;
import br.com.analisecliente.exception.RequisicaoInvalidaException;
import br.com.analisecliente.model.ProcessoAnalise;
import br.com.analisecliente.repository.ProcessoAnaliseRepository;
import br.com.analisecliente.util.RequestIdGenerator;
import br.com.analisecliente.util.ResponseFactory;

@Service
public class SolicitarAnaliseClienteService {

    private final ProcessoAnaliseRepository processoAnaliseRepository;
    private final RequestIdGenerator requestIdGenerator;
    private final AgregadorClient agregadorClient;

    public SolicitarAnaliseClienteService(ProcessoAnaliseRepository processoAnaliseRepository,
                                          RequestIdGenerator requestIdGenerator,
                                          AgregadorClient agregadorClient) {
        this.processoAnaliseRepository = processoAnaliseRepository;
        this.requestIdGenerator = requestIdGenerator;
        this.agregadorClient = agregadorClient;
    }

    public ResponsePadrao solicitar(SolicitarAnaliseClienteRequest request) {
        validarRequest(request);

        String requestId = requestIdGenerator.gerar();
        LocalDateTime agora = LocalDateTime.now();

        ProcessoAnalise processo = new ProcessoAnalise();
        processo.setRequestId(requestId);
        processo.setCpf(request.getCpf());
        processo.setStatus(StatusAnalise.PROCESSANDO);
        processo.setResultado(null);
        processo.setErro(null);
        processo.setDataHoraSolicitacao(agora);
        processo.setDataHoraAtualizacao(agora);

        processoAnaliseRepository.save(processo);

        try {
            agregadorClient.processar(requestId, request.getCpf());
        } catch (Exception ex) {
            System.out.println("Falha ao acionar o agregador para o requestId " + requestId + ": " + ex.getMessage());
        }

        return ResponseFactory.sucesso(
                requestId,
                StatusAnalise.PROCESSANDO.name(),
                "Solicitação recebida com sucesso"
        );
    }

    private void validarRequest(SolicitarAnaliseClienteRequest request) {
        if (request == null) {
            throw new RequisicaoInvalidaException("Body da requisição é obrigatório");
        }

        String cpf = request.getCpf();

        if (cpf == null || cpf.trim().isEmpty()) {
            throw new RequisicaoInvalidaException("CPF é obrigatório");
        }

        String cpfNumerico = cpf.replaceAll("\\D", "");

        if (cpfNumerico.length() != 11) {
            throw new RequisicaoInvalidaException("CPF deve conter 11 dígitos");
        }

        request.setCpf(cpfNumerico);
    }
}