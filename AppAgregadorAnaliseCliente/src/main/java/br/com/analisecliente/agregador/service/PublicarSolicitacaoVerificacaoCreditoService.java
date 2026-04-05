package br.com.analisecliente.agregador.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.dto.SolicitarVerificacaoCreditoMessage;

@Service
public class PublicarSolicitacaoVerificacaoCreditoService {

    private static final String EXCHANGE = "analise.exchange";
    private static final String ROUTING_KEY = "analise.credito.solicitar";

    private final RabbitTemplate rabbitTemplate;

    public PublicarSolicitacaoVerificacaoCreditoService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void executar(SolicitarVerificacaoCreditoMessage message) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
        System.out.println("Mensagem publicada para crédito. requestId=" + message.getRequestId());
    }
}