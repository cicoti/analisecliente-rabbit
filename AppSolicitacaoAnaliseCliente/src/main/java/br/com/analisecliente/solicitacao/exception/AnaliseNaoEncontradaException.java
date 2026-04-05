package br.com.analisecliente.solicitacao.exception;

public class AnaliseNaoEncontradaException extends RuntimeException {

    public AnaliseNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}