package br.com.analisecliente.agregador.dto;

import java.time.LocalDateTime;

public class ResultadoVerificacaoStatusClienteMessage {

    private String requestId;
    private String cpf;
    private Boolean clienteAtivo;
    private String mensagem;
    private LocalDateTime dataHoraProcessamento;

    public ResultadoVerificacaoStatusClienteMessage() {
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

    public Boolean getClienteAtivo() {
        return clienteAtivo;
    }

    public void setClienteAtivo(Boolean clienteAtivo) {
        this.clienteAtivo = clienteAtivo;
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
        return "ResultadoVerificacaoStatusClienteMessage{" +
                "requestId='" + requestId + '\'' +
                ", cpf='" + cpf + '\'' +
                ", clienteAtivo=" + clienteAtivo +
                ", mensagem='" + mensagem + '\'' +
                ", dataHoraProcessamento=" + dataHoraProcessamento +
                '}';
    }
}