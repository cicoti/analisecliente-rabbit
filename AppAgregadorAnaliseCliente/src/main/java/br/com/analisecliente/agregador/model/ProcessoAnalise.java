package br.com.analisecliente.agregador.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "processo_analise")
public class ProcessoAnalise {

    @Id
    private String id;
    private String requestId;
    private String cpf;
    private String statusProcesso;
    private LocalDateTime dataHoraSolicitacao;
    private ResultadoAnalise resultado;
    private ErroAnalise erro;

    public ProcessoAnalise() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatusProcesso() {
        return statusProcesso;
    }

    public void setStatusProcesso(String statusProcesso) {
        this.statusProcesso = statusProcesso;
    }

    public LocalDateTime getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }

    public void setDataHoraSolicitacao(LocalDateTime dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
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
}