package br.com.analisecliente.agregador.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.dto.SolicitarVerificacaoFichaLimpaMessage;

@Service
public class PublicarSolicitacaoVerificacaoFichaLimpaService {

    private static final String EXCHANGE = "analise.exchange";
    private static final String ROUTING_KEY = "analise.ficha-limpa.solicitar";

    private final RabbitTemplate rabbitTemplate;

    public PublicarSolicitacaoVerificacaoFichaLimpaService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void executar(SolicitarVerificacaoFichaLimpaMessage message) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
        System.out.println("Mensagem publicada para ficha limpa. requestId=" + message.getRequestId());
    }
}