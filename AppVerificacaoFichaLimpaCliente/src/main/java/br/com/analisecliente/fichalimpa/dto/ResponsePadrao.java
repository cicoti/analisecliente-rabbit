package br.com.analisecliente.fichalimpa.dto;

public class ResponsePadrao {

    private Object dados;
    private RetornoPadrao retorno;

    public Object getDados() {
        return dados;
    }

    public void setDados(Object dados) {
        this.dados = dados;
    }

    public RetornoPadrao getRetorno() {
        return retorno;
    }

    public void setRetorno(RetornoPadrao retorno) {
        this.retorno = retorno;
    }
}