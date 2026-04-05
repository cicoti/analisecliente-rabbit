package br.com.analisecliente.credito.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.analisecliente.credito.dto.ResultadoVerificacaoCreditoMessage;
import br.com.analisecliente.credito.dto.SolicitarVerificacaoCreditoMessage;
import br.com.analisecliente.credito.service.ProcessarVerificacaoCreditoClienteService;
import br.com.analisecliente.credito.service.PublicarResultadoVerificacaoCreditoService;

@Component
public class ProcessarVerificacaoCreditoClienteListener {

    private final ProcessarVerificacaoCreditoClienteService processarVerificacaoCreditoClienteService;
    private final PublicarResultadoVerificacaoCreditoService publicarResultadoVerificacaoCreditoService;

    public ProcessarVerificacaoCreditoClienteListener(
            ProcessarVerificacaoCreditoClienteService processarVerificacaoCreditoClienteService,
            PublicarResultadoVerificacaoCreditoService publicarResultadoVerificacaoCreditoService) {
        this.processarVerificacaoCreditoClienteService = processarVerificacaoCreditoClienteService;
        this.publicarResultadoVerificacaoCreditoService = publicarResultadoVerificacaoCreditoService;
    }

    @RabbitListener(queues = "analise.credito.solicitar.queue")
    public void executar(SolicitarVerificacaoCreditoMessage message) {
        System.out.println("Solicitação de crédito recebida: " + message);

        ResultadoVerificacaoCreditoMessage resultado =
                processarVerificacaoCreditoClienteService.executar(message);

        publicarResultadoVerificacaoCreditoService.executar(resultado);
    }
}