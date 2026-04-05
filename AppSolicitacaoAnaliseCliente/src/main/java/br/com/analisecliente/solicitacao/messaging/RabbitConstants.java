package br.com.analisecliente.solicitacao.messaging;

public final class RabbitConstants {

    private RabbitConstants() {
    }

    public static final String ANALISE_EXCHANGE = "analise.exchange";

    public static final String ANALISE_SOLICITACAO_QUEUE = "analise.solicitacao.queue";

    public static final String ANALISE_SOLICITACAO_ROUTING_KEY = "analise.solicitacao";
}