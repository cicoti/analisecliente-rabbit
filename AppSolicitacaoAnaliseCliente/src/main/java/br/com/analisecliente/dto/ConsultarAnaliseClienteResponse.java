package br.com.analisecliente.dto;

public class ConsultarAnaliseClienteResponse {

    private String requestId;
    private String statusProcesso;
    private ResultadoConsultaAnaliseClienteResponse resultado;
    private ErroConsultaAnaliseClienteResponse erro;
    private RetornoPadrao retorno;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getStatusProcesso() {
        return statusProcesso;
    }

    public void setStatusProcesso(String statusProcesso) {
        this.statusProcesso = statusProcesso;
    }

    public ResultadoConsultaAnaliseClienteResponse getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoConsultaAnaliseClienteResponse resultado) {
        this.resultado = resultado;
    }

    public ErroConsultaAnaliseClienteResponse getErro() {
        return erro;
    }

    public void setErro(ErroConsultaAnaliseClienteResponse erro) {
        this.erro = erro;
    }

    public RetornoPadrao getRetorno() {
        return retorno;
    }

    public void setRetorno(RetornoPadrao retorno) {
        this.retorno = retorno;
    }
}