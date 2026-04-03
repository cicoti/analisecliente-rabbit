package br.com.analisecliente.dto;

import java.util.Map;

public class ResultadoConsultaAnaliseClienteResponse {

    private Map<String, Object> verificacaoCredito;
    private Map<String, Object> verificacaoFichaLimpa;
    private Map<String, Object> verificacaoStatusCliente;
    private Map<String, Object> resultadoConsolidado;

    public Map<String, Object> getVerificacaoCredito() {
        return verificacaoCredito;
    }

    public void setVerificacaoCredito(Map<String, Object> verificacaoCredito) {
        this.verificacaoCredito = verificacaoCredito;
    }

    public Map<String, Object> getVerificacaoFichaLimpa() {
        return verificacaoFichaLimpa;
    }

    public void setVerificacaoFichaLimpa(Map<String, Object> verificacaoFichaLimpa) {
        this.verificacaoFichaLimpa = verificacaoFichaLimpa;
    }

    public Map<String, Object> getVerificacaoStatusCliente() {
        return verificacaoStatusCliente;
    }

    public void setVerificacaoStatusCliente(Map<String, Object> verificacaoStatusCliente) {
        this.verificacaoStatusCliente = verificacaoStatusCliente;
    }

    public Map<String, Object> getResultadoConsolidado() {
        return resultadoConsolidado;
    }

    public void setResultadoConsolidado(Map<String, Object> resultadoConsolidado) {
        this.resultadoConsolidado = resultadoConsolidado;
    }
}