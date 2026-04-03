package br.com.analisecliente.fichalimpa.dto;

public class VerificarFichaLimpaResponse {

    private String cpf;
    private Boolean possuiFichaLimpa;
    private String mensagem;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Boolean getPossuiFichaLimpa() {
        return possuiFichaLimpa;
    }

    public void setPossuiFichaLimpa(Boolean possuiFichaLimpa) {
        this.possuiFichaLimpa = possuiFichaLimpa;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}