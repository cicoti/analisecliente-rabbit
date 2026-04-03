package br.com.analisecliente.agregador.dto;

public class ResultadoAgregadoResponse {

    private String requestId;
    private String cpf;
    private VerificacaoCreditoDados verificacaoCredito;
    private VerificacaoFichaLimpaDados verificacaoFichaLimpa;
    private VerificacaoStatusClienteDados verificacaoStatusCliente;
    private Boolean aprovado;
    private String motivo;

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

    public VerificacaoCreditoDados getVerificacaoCredito() {
        return verificacaoCredito;
    }

    public void setVerificacaoCredito(VerificacaoCreditoDados verificacaoCredito) {
        this.verificacaoCredito = verificacaoCredito;
    }

    public VerificacaoFichaLimpaDados getVerificacaoFichaLimpa() {
        return verificacaoFichaLimpa;
    }

    public void setVerificacaoFichaLimpa(VerificacaoFichaLimpaDados verificacaoFichaLimpa) {
        this.verificacaoFichaLimpa = verificacaoFichaLimpa;
    }

    public VerificacaoStatusClienteDados getVerificacaoStatusCliente() {
        return verificacaoStatusCliente;
    }

    public void setVerificacaoStatusCliente(VerificacaoStatusClienteDados verificacaoStatusCliente) {
        this.verificacaoStatusCliente = verificacaoStatusCliente;
    }

    public Boolean getAprovado() {
        return aprovado;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}