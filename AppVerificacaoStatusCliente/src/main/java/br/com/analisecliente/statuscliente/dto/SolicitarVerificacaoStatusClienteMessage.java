package br.com.analisecliente.statuscliente.dto;

import java.time.LocalDateTime;

public class SolicitarVerificacaoStatusClienteMessage {

    private String requestId;
    private String cpf;
    private LocalDateTime dataHoraSolicitacao;

    public SolicitarVerificacaoStatusClienteMessage() {
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

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    @Override
    public String toString() {
        return "SolicitarVerificacaoStatusClienteMessage{" +
                "requestId='" + requestId + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataHoraSolicitacao=" + dataHoraSolicitacao +
                '}';
    }
}