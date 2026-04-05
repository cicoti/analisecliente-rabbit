package br.com.analisecliente.agregador.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.analisecliente.agregador.dto.ResultadoVerificacaoCreditoMessage;
import br.com.analisecliente.agregador.service.AtualizarResultadoVerificacaoCreditoService;

@Component
public class ProcessarResultadoVerificacaoCreditoListener {

    private final AtualizarResultadoVerificacaoCreditoService atualizarResultadoVerificacaoCreditoService;

    public ProcessarResultadoVerificacaoCreditoListener(
            AtualizarResultadoVerificacaoCreditoService atualizarResultadoVerificacaoCreditoService) {
        this.atualizarResultadoVerificacaoCreditoService = atualizarResultadoVerificacaoCreditoService;
    }

    @RabbitListener(queues = "analise.credito.resultado.queue")
    public void executar(ResultadoVerificacaoCreditoMessage message) {
        System.out.println("Resultado de crédito recebido no agregador: " + message);
        atualizarResultadoVerificacaoCreditoService.executar(message);
    }
}