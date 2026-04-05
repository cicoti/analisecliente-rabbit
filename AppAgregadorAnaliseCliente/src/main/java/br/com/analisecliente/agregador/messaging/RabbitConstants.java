package br.com.analisecliente.agregador.messaging;

public final class RabbitConstants {

    private RabbitConstants() {
    }

    public static final String ANALISE_EXCHANGE = "analise.exchange";

    public static final String ANALISE_SOLICITACAO_QUEUE = "analise.solicitacao.queue";
    public static final String ANALISE_CREDITO_SOLICITAR_QUEUE = "analise.credito.solicitar.queue";
    public static final String ANALISE_FICHA_LIMPA_SOLICITAR_QUEUE = "analise.ficha-limpa.solicitar.queue";
    public static final String ANALISE_STATUS_CLIENTE_SOLICITAR_QUEUE = "analise.status-cliente.solicitar.queue";

    public static final String ANALISE_CREDITO_RESULTADO_QUEUE = "analise.credito.resultado.queue";
    public static final String ANALISE_FICHA_LIMPA_RESULTADO_QUEUE = "analise.ficha-limpa.resultado.queue";
    public static final String ANALISE_STATUS_CLIENTE_RESULTADO_QUEUE = "analise.status-cliente.resultado.queue";

    public static final String ANALISE_SOLICITACAO_ROUTING_KEY = "analise.solicitacao";
    public static final String ANALISE_CREDITO_SOLICITAR_ROUTING_KEY = "analise.credito.solicitar";
    public static final String ANALISE_FICHA_LIMPA_SOLICITAR_ROUTING_KEY = "analise.ficha-limpa.solicitar";
    public static final String ANALISE_STATUS_CLIENTE_SOLICITAR_ROUTING_KEY = "analise.status-cliente.solicitar";

    public static final String ANALISE_CREDITO_RESULTADO_ROUTING_KEY = "analise.credito.resultado";
    public static final String ANALISE_FICHA_LIMPA_RESULTADO_ROUTING_KEY = "analise.ficha-limpa.resultado";
    public static final String ANALISE_STATUS_CLIENTE_RESULTADO_ROUTING_KEY = "analise.status-cliente.resultado";
}