package br.com.analisecliente.agregador.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.dto.SolicitarVerificacaoStatusClienteMessage;

@Service
public class PublicarSolicitacaoVerificacaoStatusClienteService {

    private static final String EXCHANGE = "analise.exchange";
    private static final String ROUTING_KEY = "analise.status-cliente.solicitar";

    private final RabbitTemplate rabbitTemplate;

    public PublicarSolicitacaoVerificacaoStatusClienteService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void executar(SolicitarVerificacaoStatusClienteMessage message) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
        System.out.println("Mensagem publicada para status do cliente. requestId=" + message.getRequestId());
    }
}