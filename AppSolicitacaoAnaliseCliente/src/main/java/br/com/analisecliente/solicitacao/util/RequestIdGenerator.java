package br.com.analisecliente.solicitacao.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class RequestIdGenerator {

    public String gerar() {
        return UUID.randomUUID().toString();
    }
}