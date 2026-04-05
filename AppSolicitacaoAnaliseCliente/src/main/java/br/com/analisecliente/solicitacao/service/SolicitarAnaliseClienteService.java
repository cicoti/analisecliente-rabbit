package br.com.analisecliente.solicitacao.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.analisecliente.solicitacao.dto.ResponsePadrao;
import br.com.analisecliente.solicitacao.dto.SolicitacaoAnaliseMessage;
import br.com.analisecliente.solicitacao.dto.SolicitarAnaliseClienteRequest;
import br.com.analisecliente.solicitacao.enums.StatusAnalise;
import br.com.analisecliente.solicitacao.exception.RequisicaoInvalidaException;
import br.com.analisecliente.solicitacao.messaging.SolicitacaoAnalisePublisher;
import br.com.analisecliente.solicitacao.model.ErroAnalise;
import br.com.analisecliente.solicitacao.model.ProcessoAnalise;
import br.com.analisecliente.solicitacao.repository.ProcessoAnaliseRepository;
import br.com.analisecliente.solicitacao.util.ResponseFactory;

@Service
public class SolicitarAnaliseClienteService {

    private final ProcessoAnaliseRepository processoAnaliseRepository;
    private final SolicitacaoAnalisePublisher solicitacaoAnalisePublisher;

    public SolicitarAnaliseClienteService(ProcessoAnaliseRepository processoAnaliseRepository,
                                          SolicitacaoAnalisePublisher solicitacaoAnalisePublisher) {
        this.processoAnaliseRepository = processoAnaliseRepository;
        this.solicitacaoAnalisePublisher = solicitacaoAnalisePublisher;
    }

    public ResponsePadrao solicitarAnalise(SolicitarAnaliseClienteRequest request) {
        validarRequest(request);

        String cpfNumerico = request.getCpf();
        String requestId = UUID.randomUUID().toString();
        LocalDateTime agora = LocalDateTime.now();

        ProcessoAnalise processo = new ProcessoAnalise();
        processo.setRequestId(requestId);
        processo.setCpf(cpfNumerico);
        processo.setStatus(StatusAnalise.PROCESSANDO.name());
        processo.setResultado(null);
        processo.setErro(null);
        processo.setDataHoraCriacao(agora);
        processo.setDataHoraAtualizacao(agora);

        processoAnaliseRepository.save(processo);

        try {
            SolicitacaoAnaliseMessage message = new SolicitacaoAnaliseMessage();
            message.setRequestId(requestId);
            message.setCpf(cpfNumerico);
            message.setOrigem("AppSolicitacaoAnaliseCliente");
            message.setDataHoraSolicitacao(agora);

            solicitacaoAnalisePublisher.publicar(message);

            return ResponseFactory.criarRespostaSucessoProcessando(
                    requestId,
                    "Solicitação recebida com sucesso"
            );

        } catch (Exception ex) {
            ErroAnalise erro = new ErroAnalise();
            erro.setOrigem("AppSolicitacaoAnaliseCliente");
            erro.setMensagem("Falha ao publicar solicitação na fila: " + ex.getMessage());

            processo.setStatus(StatusAnalise.ERRO.name());
            processo.setErro(erro);
            processo.setDataHoraAtualizacao(LocalDateTime.now());

            processoAnaliseRepository.save(processo);

            throw new RuntimeException("Erro ao enviar solicitação para processamento assíncrono", ex);
        }
    }

    private void validarRequest(SolicitarAnaliseClienteRequest request) {
        if (request == null) {
            throw new RequisicaoInvalidaException("Body da requisição é obrigatório");
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
}