package br.com.analisecliente.agregador.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.analisecliente.agregador.dto.ResultadoVerificacaoStatusClienteMessage;
import br.com.analisecliente.agregador.service.AtualizarResultadoVerificacaoStatusClienteService;

@Component
public class ProcessarResultadoVerificacaoStatusClienteListener {

    private final AtualizarResultadoVerificacaoStatusClienteService atualizarResultadoVerificacaoStatusClienteService;

    public ProcessarResultadoVerificacaoStatusClienteListener(
            AtualizarResultadoVerificacaoStatusClienteService atualizarResultadoVerificacaoStatusClienteService) {
        this.atualizarResultadoVerificacaoStatusClienteService = atualizarResultadoVerificacaoStatusClienteService;
    }

    @RabbitListener(queues = "analise.status-cliente.resultado.queue")
    public void executar(ResultadoVerificacaoStatusClienteMessage message) {
        System.out.println("Resultado de status do cliente recebido no agregador: " + message);
        atualizarResultadoVerificacaoStatusClienteService.executar(message);
    }
}