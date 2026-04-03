package br.com.analisecliente.exception;

public class AnaliseNaoEncontradaException extends RuntimeException {

    public AnaliseNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}