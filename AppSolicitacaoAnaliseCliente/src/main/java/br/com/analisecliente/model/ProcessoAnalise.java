package br.com.analisecliente.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.analisecliente.enums.StatusAnalise;

@Document(collection = "processos")
public class ProcessoAnalise {

    @Id
    private String requestId;
    private String cpf;
    private StatusAnalise status;
    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime dataHoraAtualizacao;
    private ResultadoAnalise resultado;
    private ErroAnalise erro;

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