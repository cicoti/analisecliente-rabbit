package br.com.analisecliente.agregador.exception;

public class ProcessoNaoEncontradoException extends RuntimeException {

    public ProcessoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}