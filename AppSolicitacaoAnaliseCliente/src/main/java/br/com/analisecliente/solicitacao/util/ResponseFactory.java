package br.com.analisecliente.solicitacao.util;

import java.time.LocalDateTime;

import br.com.analisecliente.solicitacao.dto.ResponsePadrao;
import br.com.analisecliente.solicitacao.dto.RetornoPadrao;
import br.com.analisecliente.solicitacao.enums.StatusAnalise;

public final class ResponseFactory {

    private ResponseFactory() {
    }

    public static ResponsePadrao criarRespostaSucessoProcessando(String requestId, String detalhe) {
        ResponsePadrao response = new ResponsePadrao();
        response.setRequestId(requestId);
        response.setStatusProcesso(StatusAnalise.PROCESSANDO.name());
        response.setRetorno(criarRetornoSucesso(detalhe));
        return response;
    }

    public static ResponsePadrao criarRespostaErro(String requestId,
                                                   String statusProcesso,
                                                   int codigo,
                                                   String chave,
                                                   String mensagem,
                                                   String detalhe) {
        ResponsePadrao response = new ResponsePadrao();
        response.setRequestId(requestId);
        response.setStatusProcesso(statusProcesso);
        response.setRetorno(criarRetornoErro(codigo, chave, mensagem, detalhe));
        return response;
    }

    public static RetornoPadrao criarRetornoSucesso(String detalhe) {
        return criarRetorno(
                0,
                "SUCESSO",
                "Sucesso",
                detalhe
        );
    }

    public static RetornoPadrao criarRetornoErro(int codigo,
                                                 String chave,
                                                 String mensagem,
                                                 String detalhe) {
        return criarRetorno(
                codigo,
                chave,
                mensagem,
                detalhe
        );
    }

    private static RetornoPadrao criarRetorno(int codigo,
                                              String chave,
                                              String mensagem,
                                              String detalhe) {
        RetornoPadrao retorno = new RetornoPadrao();
        retorno.setCodigo(codigo);
        retorno.setChave(chave);
        retorno.setMensagem(mensagem);
        retorno.setDetalhe(detalhe);
        retorno.setDataHora(LocalDateTime.now());
        return retorno;
    }
}