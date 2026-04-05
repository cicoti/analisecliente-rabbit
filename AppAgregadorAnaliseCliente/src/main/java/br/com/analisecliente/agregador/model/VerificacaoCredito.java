package br.com.analisecliente.agregador.model;

import java.time.LocalDateTime;

public class VerificacaoCredito {

    private Boolean possuiCredito;
    private String mensagem;
    private LocalDateTime dataHoraProcessamento;

    public VerificacaoCredito() {
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
}