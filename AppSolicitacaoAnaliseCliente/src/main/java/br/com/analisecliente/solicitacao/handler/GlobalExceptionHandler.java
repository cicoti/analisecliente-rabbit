package br.com.analisecliente.solicitacao.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.analisecliente.solicitacao.dto.ResponsePadrao;
import br.com.analisecliente.solicitacao.exception.AnaliseNaoEncontradaException;
import br.com.analisecliente.solicitacao.exception.RequisicaoInvalidaException;
import br.com.analisecliente.solicitacao.util.ResponseFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AnaliseNaoEncontradaException.class)
    public ResponseEntity<ResponsePadrao> tratarAnaliseNaoEncontrada(AnaliseNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseFactory.criarRespostaErro(
                        null,
                        null,
                        -2,
                        "ANALISE_NAO_ENCONTRADA",
                        "Erro de negócio",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponsePadrao> tratarJsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseFactory.criarRespostaErro(
                        null,
                        null,
                        -3,
                        "JSON_INVALIDO",
                        "Erro de validação",
                        "JSON inválido na requisição"
                ));
    }

    @ExceptionHandler(RequisicaoInvalidaException.class)
    public ResponseEntity<ResponsePadrao> tratarRequisicaoInvalida(RequisicaoInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseFactory.criarRespostaErro(
                        null,
                        null,
                        -3,
                        "REQUISICAO_INVALIDA",
                        "Erro de validação",
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponsePadrao> tratarErroGenerico(Exception ex) {
        LOGGER.error("Erro interno ao processar a solicitação", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseFactory.criarRespostaErro(
                        null,
                        null,
                        -1,
                        "ERRO_SISTEMICO",
                        "Erro sistêmico",
                        "Erro interno ao processar a solicitação"
                ));
    }
}