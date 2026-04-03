package br.com.analisecliente.dto;

public class ErroConsultaAnaliseClienteResponse {

    private String origem;
    private String mensagem;

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}