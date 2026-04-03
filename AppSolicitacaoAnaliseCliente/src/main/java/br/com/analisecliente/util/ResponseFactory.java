package br.com.analisecliente.util;

import java.time.LocalDateTime;

import br.com.analisecliente.dto.ResponsePadrao;
import br.com.analisecliente.dto.RetornoPadrao;

public class ResponseFactory {

    public static ResponsePadrao sucesso(String requestId, String statusProcesso, String detalhe) {
        ResponsePadrao response = new ResponsePadrao();
        response.setRequestId(requestId);
        response.setStatusProcesso(statusProcesso);
        response.setRetorno(retornoSucesso(detalhe));
        return response;
    }

    public static ResponsePadrao erro(String requestId,
                                      String statusProcesso,
                                      int codigo,
                                      String chave,
                                      String mensagem,
                                      String detalhe) {
        ResponsePadrao response = new ResponsePadrao();
        response.setRequestId(requestId);
        response.setStatusProcesso(statusProcesso);
        response.setRetorno(retornoErro(codigo, chave, mensagem, detalhe));
        return response;
    }

    public static RetornoPadrao retornoSucesso(String detalhe) {
        return criarRetorno(0, "SUCESSO", "Sucesso", detalhe);
    }

    public static RetornoPadrao retornoErro(int codigo,
                                            String chave,
                                            String mensagem,
                                            String detalhe) {
        return criarRetorno(codigo, chave, mensagem, detalhe);
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