package br.com.analisecliente.agregador.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.analisecliente.agregador.dto.SolicitacaoAnaliseMessage;
import br.com.analisecliente.agregador.dto.SolicitarVerificacaoCreditoMessage;
import br.com.analisecliente.agregador.dto.SolicitarVerificacaoFichaLimpaMessage;
import br.com.analisecliente.agregador.dto.SolicitarVerificacaoStatusClienteMessage;
import br.com.analisecliente.agregador.service.PublicarSolicitacaoVerificacaoCreditoService;
import br.com.analisecliente.agregador.service.PublicarSolicitacaoVerificacaoFichaLimpaService;
import br.com.analisecliente.agregador.service.PublicarSolicitacaoVerificacaoStatusClienteService;

@Component
public class ProcessarSolicitacaoAnaliseClienteListener {

    private final PublicarSolicitacaoVerificacaoCreditoService publicarSolicitacaoVerificacaoCreditoService;
    private final PublicarSolicitacaoVerificacaoFichaLimpaService publicarSolicitacaoVerificacaoFichaLimpaService;
    private final PublicarSolicitacaoVerificacaoStatusClienteService publicarSolicitacaoVerificacaoStatusClienteService;

    public ProcessarSolicitacaoAnaliseClienteListener(
            PublicarSolicitacaoVerificacaoCreditoService publicarSolicitacaoVerificacaoCreditoService,
            PublicarSolicitacaoVerificacaoFichaLimpaService publicarSolicitacaoVerificacaoFichaLimpaService,
            PublicarSolicitacaoVerificacaoStatusClienteService publicarSolicitacaoVerificacaoStatusClienteService) {
        this.publicarSolicitacaoVerificacaoCreditoService = publicarSolicitacaoVerificacaoCreditoService;
        this.publicarSolicitacaoVerificacaoFichaLimpaService = publicarSolicitacaoVerificacaoFichaLimpaService;
        this.publicarSolicitacaoVerificacaoStatusClienteService = publicarSolicitacaoVerificacaoStatusClienteService;
    }

    @RabbitListener(queues = "analise.solicitacao.queue")
    public void executar(SolicitacaoAnaliseMessage message) {
        System.out.println("Solicitação recebida no agregador: " + message);

        SolicitarVerificacaoCreditoMessage creditoMessage = criarSolicitarVerificacaoCreditoMessage(message);
        SolicitarVerificacaoFichaLimpaMessage fichaLimpaMessage = criarSolicitarVerificacaoFichaLimpaMessage(message);
        SolicitarVerificacaoStatusClienteMessage statusClienteMessage = criarSolicitarVerificacaoStatusClienteMessage(message);

        publicarSolicitacaoVerificacaoCreditoService.executar(creditoMessage);
        publicarSolicitacaoVerificacaoFichaLimpaService.executar(fichaLimpaMessage);
        publicarSolicitacaoVerificacaoStatusClienteService.executar(statusClienteMessage);

        System.out.println("Fan out realizado com sucesso. requestId=" + message.getRequestId());
    }

    private SolicitarVerificacaoCreditoMessage criarSolicitarVerificacaoCreditoMessage(SolicitacaoAnaliseMessage message) {
        SolicitarVerificacaoCreditoMessage novaMessage = new SolicitarVerificacaoCreditoMessage();
        novaMessage.setRequestId(message.getRequestId());
        novaMessage.setCpf(message.getCpf());
        novaMessage.setDataHoraSolicitacao(message.getDataHoraSolicitacao());
        return novaMessage;
    }

    private SolicitarVerificacaoFichaLimpaMessage criarSolicitarVerificacaoFichaLimpaMessage(SolicitacaoAnaliseMessage message) {
        SolicitarVerificacaoFichaLimpaMessage novaMessage = new SolicitarVerificacaoFichaLimpaMessage();
        novaMessage.setRequestId(message.getRequestId());
        novaMessage.setCpf(message.getCpf());
        novaMessage.setDataHoraSolicitacao(message.getDataHoraSolicitacao());
        return novaMessage;
    }

    private SolicitarVerificacaoStatusClienteMessage criarSolicitarVerificacaoStatusClienteMessage(SolicitacaoAnaliseMessage message) {
        SolicitarVerificacaoStatusClienteMessage novaMessage = new SolicitarVerificacaoStatusClienteMessage();
        novaMessage.setRequestId(message.getRequestId());
        novaMessage.setCpf(message.getCpf());
        novaMessage.setDataHoraSolicitacao(message.getDataHoraSolicitacao());
        return novaMessage;
    }
}