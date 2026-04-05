package br.com.analisecliente.agregador.dto;

import java.time.LocalDateTime;

public class ResultadoVerificacaoCreditoMessage {

    private String requestId;
    private String cpf;
    private Boolean possuiCredito;
    private String mensagem;
    private LocalDateTime dataHoraProcessamento;

    public ResultadoVerificacaoCreditoMessage() {
    }

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

    public LocalDateTime getDataHoraProcessamento() {
        return dataHoraProcessamento;
    }

    public void setDataHoraProcessamento(LocalDateTime dataHoraProcessamento) {
        this.dataHoraProcessamento = dataHoraProcessamento;
    }

    @Override
    public String toString() {
        return "ResultadoVerificacaoCreditoMessage{" +
                "requestId='" + requestId + '\'' +
                ", cpf='" + cpf + '\'' +
                ", possuiCredito=" + possuiCredito +
                ", mensagem='" + mensagem + '\'' +
                ", dataHoraProcessamento=" + dataHoraProcessamento +
                '}';
    }
}