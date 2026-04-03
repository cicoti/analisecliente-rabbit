package br.com.analisecliente.dto;

import java.time.LocalDateTime;

public class ErroResponse {

    private int status;
    private String mensagem;
    private LocalDateTime dataHora;

    public ErroResponse() {
    }

    public ErroResponse(int status, String mensagem, LocalDateTime dataHora) {
        this.status = status;
        this.mensagem = mensagem;
        this.dataHora = dataHora;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}