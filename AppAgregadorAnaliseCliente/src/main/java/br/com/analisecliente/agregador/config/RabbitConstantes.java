package br.com.analisecliente.agregador.config;

public final class RabbitConstantes {

    public static final String EXCHANGE = "analise.exchange";

    public static final String SOLICITACAO_QUEUE = "analise.solicitacao.queue";
    public static final String CREDITO_SOLICITAR_QUEUE = "analise.credito.solicitar.queue";
    public static final String FICHA_LIMPA_SOLICITAR_QUEUE = "analise.ficha-limpa.solicitar.queue";
    public static final String STATUS_CLIENTE_SOLICITAR_QUEUE = "analise.status-cliente.solicitar.queue";

    public static final String SOLICITACAO_ROUTING_KEY = "analise.solicitacao";
    public static final String CREDITO_SOLICITAR_ROUTING_KEY = "analise.credito.solicitar";
    public static final String FICHA_LIMPA_SOLICITAR_ROUTING_KEY = "analise.ficha-limpa.solicitar";
    public static final String STATUS_CLIENTE_SOLICITAR_ROUTING_KEY = "analise.status-cliente.solicitar";

    private RabbitConstantes() {
    }
}