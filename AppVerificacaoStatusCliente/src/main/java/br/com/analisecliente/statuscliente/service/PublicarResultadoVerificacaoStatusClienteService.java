package br.com.analisecliente.statuscliente.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.analisecliente.statuscliente.dto.ResultadoVerificacaoStatusClienteMessage;

@Service
public class PublicarResultadoVerificacaoStatusClienteService {

    private static final String EXCHANGE = "analise.exchange";
    private static final String ROUTING_KEY = "analise.status-cliente.resultado";

    private final RabbitTemplate rabbitTemplate;

    public PublicarResultadoVerificacaoStatusClienteService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void executar(ResultadoVerificacaoStatusClienteMessage message) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
        System.out.println("Resultado de status do cliente publicado. requestId=" + message.getRequestId());
    }
}