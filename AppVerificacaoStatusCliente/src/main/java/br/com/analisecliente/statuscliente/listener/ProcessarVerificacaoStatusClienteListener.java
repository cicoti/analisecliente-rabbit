package br.com.analisecliente.statuscliente.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.analisecliente.statuscliente.dto.ResultadoVerificacaoStatusClienteMessage;
import br.com.analisecliente.statuscliente.dto.SolicitarVerificacaoStatusClienteMessage;
import br.com.analisecliente.statuscliente.service.ProcessarVerificacaoStatusClienteService;
import br.com.analisecliente.statuscliente.service.PublicarResultadoVerificacaoStatusClienteService;

@Component
public class ProcessarVerificacaoStatusClienteListener {

    private final ProcessarVerificacaoStatusClienteService processarVerificacaoStatusClienteService;
    private final PublicarResultadoVerificacaoStatusClienteService publicarResultadoVerificacaoStatusClienteService;

    public ProcessarVerificacaoStatusClienteListener(
            ProcessarVerificacaoStatusClienteService processarVerificacaoStatusClienteService,
            PublicarResultadoVerificacaoStatusClienteService publicarResultadoVerificacaoStatusClienteService) {
        this.processarVerificacaoStatusClienteService = processarVerificacaoStatusClienteService;
        this.publicarResultadoVerificacaoStatusClienteService = publicarResultadoVerificacaoStatusClienteService;
    }

    @RabbitListener(queues = "analise.status-cliente.solicitar.queue")
    public void executar(SolicitarVerificacaoStatusClienteMessage message) {
        System.out.println("Solicitação de status do cliente recebida: " + message);

        ResultadoVerificacaoStatusClienteMessage resultado =
                processarVerificacaoStatusClienteService.executar(message);

        publicarResultadoVerificacaoStatusClienteService.executar(resultado);
    }
}