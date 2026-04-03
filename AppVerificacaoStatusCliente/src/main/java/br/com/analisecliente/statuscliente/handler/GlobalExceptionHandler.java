package br.com.analisecliente.statuscliente.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.analisecliente.statuscliente.dto.ResponsePadrao;
import br.com.analisecliente.statuscliente.exception.RequisicaoInvalidaException;
import br.com.analisecliente.statuscliente.util.ResponseFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RequisicaoInvalidaException.class)
    public ResponseEntity<ResponsePadrao> handleRequisicaoInvalida(RequisicaoInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseFactory.erro(null, -3, "REQUISICAO_INVALIDA", "Erro de validação", ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponsePadrao> handleJsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseFactory.erro(null, -3, "JSON_INVALIDO", "Erro de validação", "JSON inválido na requisição"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponsePadrao> handleErroGenerico(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseFactory.erro(null, -1, "ERRO_SISTEMICO", "Erro sistêmico", "Erro interno ao processar a solicitação"));
    }
}