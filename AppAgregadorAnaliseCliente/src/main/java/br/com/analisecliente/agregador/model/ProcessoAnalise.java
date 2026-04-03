package br.com.analisecliente.agregador.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.analisecliente.agregador.enums.StatusAnalise;

@Document(collection = "processos")
public class ProcessoAnalise {

    @Id
    private String requestId;
    private String cpf;
    private StatusAnalise status;
    private ResultadoAnalise resultado;
    private ErroAnalise erro;
    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime dataHoraAtualizacao;

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

    public StatusAnalise getStatus() {
        return status;
    }

    public void setStatus(StatusAnalise status) {
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

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }

    public LocalDateTime getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public void setDataHoraAtualizacao(LocalDateTime dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }
}