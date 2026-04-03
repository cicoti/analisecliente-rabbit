package br.com.analisecliente.credito.dto;

public class VerificarCreditoResponse {

    private String cpf;
    private Boolean possuiCredito;
    private String mensagem;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Boolean getPossuiCredito() {
        return possuiCredito;
    }

    public void setPossuiCredito(Boolean possuiCredito) {
        this.possuiCredito = possuiCredito;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}