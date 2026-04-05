package br.com.analisecliente.agregador.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.analisecliente.agregador.dto.ResultadoVerificacaoFichaLimpaMessage;
import br.com.analisecliente.agregador.service.AtualizarResultadoVerificacaoFichaLimpaService;

@Component
public class ProcessarResultadoVerificacaoFichaLimpaListener {

    private final AtualizarResultadoVerificacaoFichaLimpaService atualizarResultadoVerificacaoFichaLimpaService;

    public ProcessarResultadoVerificacaoFichaLimpaListener(
            AtualizarResultadoVerificacaoFichaLimpaService atualizarResultadoVerificacaoFichaLimpaService) {
        this.atualizarResultadoVerificacaoFichaLimpaService = atualizarResultadoVerificacaoFichaLimpaService;
    }

    @RabbitListener(queues = "analise.ficha-limpa.resultado.queue")
    public void executar(ResultadoVerificacaoFichaLimpaMessage message) {
        System.out.println("Resultado de ficha limpa recebido no agregador: " + message);
        atualizarResultadoVerificacaoFichaLimpaService.executar(message);
    }
}