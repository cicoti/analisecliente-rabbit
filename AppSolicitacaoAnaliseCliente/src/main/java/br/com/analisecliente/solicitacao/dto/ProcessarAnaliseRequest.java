package br.com.analisecliente.solicitacao.dto;

public class ProcessarAnaliseRequest {

    private String requestId;
    private String cpf;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}