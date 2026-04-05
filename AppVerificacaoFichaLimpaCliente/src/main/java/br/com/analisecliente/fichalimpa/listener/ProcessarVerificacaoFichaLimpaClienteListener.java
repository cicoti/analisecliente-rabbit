package br.com.analisecliente.fichalimpa.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.analisecliente.fichalimpa.dto.ResultadoVerificacaoFichaLimpaMessage;
import br.com.analisecliente.fichalimpa.dto.SolicitarVerificacaoFichaLimpaMessage;
import br.com.analisecliente.fichalimpa.service.ProcessarVerificacaoFichaLimpaClienteService;
import br.com.analisecliente.fichalimpa.service.PublicarResultadoVerificacaoFichaLimpaService;

@Component
public class ProcessarVerificacaoFichaLimpaClienteListener {

    private final ProcessarVerificacaoFichaLimpaClienteService processarVerificacaoFichaLimpaClienteService;
    private final PublicarResultadoVerificacaoFichaLimpaService publicarResultadoVerificacaoFichaLimpaService;

    public ProcessarVerificacaoFichaLimpaClienteListener(
            ProcessarVerificacaoFichaLimpaClienteService processarVerificacaoFichaLimpaClienteService,
            PublicarResultadoVerificacaoFichaLimpaService publicarResultadoVerificacaoFichaLimpaService) {
        this.processarVerificacaoFichaLimpaClienteService = processarVerificacaoFichaLimpaClienteService;
        this.publicarResultadoVerificacaoFichaLimpaService = publicarResultadoVerificacaoFichaLimpaService;
    }

    @RabbitListener(queues = "analise.ficha-limpa.solicitar.queue")
    public void executar(SolicitarVerificacaoFichaLimpaMessage message) {
        System.out.println("Solicitação de ficha limpa recebida: " + message);

        ResultadoVerificacaoFichaLimpaMessage resultado =
                processarVerificacaoFichaLimpaClienteService.executar(message);

        publicarResultadoVerificacaoFichaLimpaService.executar(resultado);
    }
}