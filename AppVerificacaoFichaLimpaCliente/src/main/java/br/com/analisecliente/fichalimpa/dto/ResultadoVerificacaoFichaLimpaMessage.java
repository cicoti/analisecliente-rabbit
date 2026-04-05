package br.com.analisecliente.fichalimpa.dto;

import java.time.LocalDateTime;

public class ResultadoVerificacaoFichaLimpaMessage {

    private String requestId;
    private String cpf;
    private Boolean possuiFichaLimpa;
    private String mensagem;
    private LocalDateTime dataHoraProcessamento;

    public ResultadoVerificacaoFichaLimpaMessage() {
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

    public LocalDateTime getDataHoraProcessamento() {
        return dataHoraProcessamento;
    }

    public void setDataHoraProcessamento(LocalDateTime dataHoraProcessamento) {
        this.dataHoraProcessamento = dataHoraProcessamento;
    }

    @Override
    public String toString() {
        return "ResultadoVerificacaoFichaLimpaMessage{" +
                "requestId='" + requestId + '\'' +
                ", cpf='" + cpf + '\'' +
                ", possuiFichaLimpa=" + possuiFichaLimpa +
                ", mensagem='" + mensagem + '\'' +
                ", dataHoraProcessamento=" + dataHoraProcessamento +
                '}';
    }
}