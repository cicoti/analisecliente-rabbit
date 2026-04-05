package br.com.analisecliente;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.analisecliente.solicitacao.dto.SolicitacaoAnaliseMessage;
import br.com.analisecliente.solicitacao.messaging.SolicitacaoAnalisePublisher;

@Configuration
public class RabbitTestePublisherConfig {

    @Bean
    public ApplicationRunner testePublicacaoRunner(SolicitacaoAnalisePublisher publisher) {
        return args -> {
            SolicitacaoAnaliseMessage message = new SolicitacaoAnaliseMessage();
            message.setRequestId(UUID.randomUUID().toString());
            message.setCpf("12345678900");
            message.setOrigem("AppSolicitacaoAnaliseCliente");
            message.setDataHoraSolicitacao(LocalDateTime.now());

            publisher.publicar(message);

            System.out.println("Mensagem de teste publicada com sucesso.");
        };
    }
}