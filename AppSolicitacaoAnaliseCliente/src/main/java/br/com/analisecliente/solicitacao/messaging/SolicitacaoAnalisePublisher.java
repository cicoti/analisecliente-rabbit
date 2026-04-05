package br.com.analisecliente.solicitacao.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import br.com.analisecliente.solicitacao.dto.SolicitacaoAnaliseMessage;

@Component
public class SolicitacaoAnalisePublisher {

    private final RabbitTemplate rabbitTemplate;

    public SolicitacaoAnalisePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicar(SolicitacaoAnaliseMessage message) {
        rabbitTemplate.convertAndSend(
                RabbitConstants.ANALISE_EXCHANGE,
                RabbitConstants.ANALISE_SOLICITACAO_ROUTING_KEY,
                message
        );

        System.out.println("Mensagem publicada na fila de solicitação. requestId=" + message.getRequestId());
    }
}