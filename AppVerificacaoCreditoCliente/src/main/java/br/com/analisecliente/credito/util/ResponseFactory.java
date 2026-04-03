package br.com.analisecliente.credito.util;

import java.time.LocalDateTime;

import br.com.analisecliente.credito.dto.ResponsePadrao;
import br.com.analisecliente.credito.dto.RetornoPadrao;

public class ResponseFactory {

    public static ResponsePadrao sucesso(Object dados, String detalhe) {
        RetornoPadrao retorno = new RetornoPadrao();
        retorno.setCodigo(0);
        retorno.setChave("SUCESSO");
        retorno.setMensagem("Sucesso");
        retorno.setDetalhe(detalhe);
        retorno.setDataHora(LocalDateTime.now());

        ResponsePadrao response = new ResponsePadrao();
        response.setDados(dados);
        response.setRetorno(retorno);

        return response;
    }

    public static ResponsePadrao erro(Object dados,
                                      int codigo,
                                      String chave,
                                      String mensagem,
                                      String detalhe) {
        RetornoPadrao retorno = new RetornoPadrao();
        retorno.setCodigo(codigo);
        retorno.setChave(chave);
        retorno.setMensagem(mensagem);
        retorno.setDetalhe(detalhe);
        retorno.setDataHora(LocalDateTime.now());

        ResponsePadrao response = new ResponsePadrao();
        response.setDados(dados);
        response.setRetorno(retorno);

        return response;
    }
}