package br.com.analisecliente.fichalimpa.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.analisecliente.fichalimpa.dto.ResultadoVerificacaoFichaLimpaMessage;

@Service
public class PublicarResultadoVerificacaoFichaLimpaService {

    private static final String EXCHANGE = "analise.exchange";
    private static final String ROUTING_KEY = "analise.ficha-limpa.resultado";

    private final RabbitTemplate rabbitTemplate;

    public PublicarResultadoVerificacaoFichaLimpaService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void executar(ResultadoVerificacaoFichaLimpaMessage message) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
        System.out.println("Resultado de ficha limpa publicado. requestId=" + message.getRequestId());
    }
}