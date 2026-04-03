package br.com.analisecliente.handler;

import br.com.analisecliente.dto.ResponsePadrao;
import br.com.analisecliente.exception.AnaliseNaoEncontradaException;
import br.com.analisecliente.exception.RequisicaoInvalidaException;
import br.com.analisecliente.util.ResponseFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AnaliseNaoEncontradaException.class)
    public ResponseEntity<ResponsePadrao> handleAnaliseNaoEncontrada(AnaliseNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseFactory.erro(null, null, -2, "ANALISE_NAO_ENCONTRADA", "Erro de negócio", ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponsePadrao> handleJsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseFactory.erro(null, null, -3, "JSON_INVALIDO", "Erro de validação", "JSON inválido na requisição"));
    }

    @ExceptionHandler(RequisicaoInvalidaException.class)
    public ResponseEntity<ResponsePadrao> handleRequisicaoInvalida(RequisicaoInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseFactory.erro(null, null, -3, "REQUISICAO_INVALIDA", "Erro de validação", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponsePadrao> handleErroGenerico(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseFactory.erro(null, null, -1, "ERRO_SISTEMICO", "Erro sistêmico", "Erro interno ao processar a solicitação"));
    }
}