package br.com.analisecliente.agregador.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.analisecliente.agregador.dto.SolicitacaoAnaliseMessage;

@Component
public class SolicitacaoAnaliseListener {

    @RabbitListener(queues = RabbitConstants.ANALISE_SOLICITACAO_QUEUE)
    public void consumir(SolicitacaoAnaliseMessage message) {
        System.out.println("Mensagem recebida no agregador");
        System.out.println("requestId: " + message.getRequestId());
        System.out.println("cpf: " + message.getCpf());
        System.out.println("origem: " + message.getOrigem());
        System.out.println("dataHoraSolicitacao: " + message.getDataHoraSolicitacao());
    }
}