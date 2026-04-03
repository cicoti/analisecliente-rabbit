package br.com.analisecliente.agregador.dto;

public class VerificacaoStatusClienteDados {

    private String cpf;
    private Boolean clienteAtivo;
    private String mensagem;

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
}