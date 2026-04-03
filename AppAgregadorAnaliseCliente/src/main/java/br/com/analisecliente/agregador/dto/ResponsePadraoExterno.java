package br.com.analisecliente.agregador.dto;

public class ResponsePadraoExterno {

    private Object dados;
    private RetornoPadraoExterno retorno;

    public Object getDados() {
        return dados;
    }

    public void setDados(Object dados) {
        this.dados = dados;
    }

    public RetornoPadraoExterno getRetorno() {
        return retorno;
    }

    public void setRetorno(RetornoPadraoExterno retorno) {
        this.retorno = retorno;
    }
}