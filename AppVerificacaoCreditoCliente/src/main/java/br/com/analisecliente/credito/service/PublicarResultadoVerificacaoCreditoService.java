package br.com.analisecliente.credito.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.analisecliente.credito.dto.ResultadoVerificacaoCreditoMessage;

@Service
public class PublicarResultadoVerificacaoCreditoService {

    private static final String EXCHANGE = "analise.exchange";
    private static final String ROUTING_KEY = "analise.credito.resultado";

    private final RabbitTemplate rabbitTemplate;

    public PublicarResultadoVerificacaoCreditoService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void executar(ResultadoVerificacaoCreditoMessage message) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
        System.out.println("Resultado de crédito publicado. requestId=" + message.getRequestId());
    }
}