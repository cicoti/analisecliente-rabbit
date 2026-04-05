package br.com.analisecliente.solicitacao.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "processos_analise")
public class ProcessoAnalise {

    @Id
    private String requestId;
    private String cpf;
    private String status;
    private ResultadoAnalise resultado;
    private ErroAnalise erro;
    private LocalDateTime dataHoraCriacao;
    private LocalDateTime dataHoraAtualizacao;

    public ProcessoAnalise() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ResultadoAnalise getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoAnalise resultado) {
        this.resultado = resultado;
    }

    public ErroAnalise getErro() {
        return erro;
    }

    public void setErro(ErroAnalise erro) {
        this.erro = erro;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void setDataHoraCriacao(LocalDateTime dataHoraCriacao) {
        this.dataHoraCriacao = dataHoraCriacao;
    }

    public LocalDateTime getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public void setDataHoraAtualizacao(LocalDateTime dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }
}