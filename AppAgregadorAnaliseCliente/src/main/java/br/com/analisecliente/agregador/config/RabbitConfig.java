package br.com.analisecliente.agregador.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.analisecliente.agregador.messaging.RabbitConstants;

@Configuration
public class RabbitConfig {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public ApplicationRunner rabbitInitializer(RabbitAdmin rabbitAdmin) {
        return args -> {
            System.out.println("Inicializando RabbitAdmin...");
            rabbitAdmin.initialize();
            System.out.println("RabbitAdmin inicializado.");
        };
    }

    @Bean
    public String testeRabbitConfig() {
        System.out.println("RabbitConfig carregada");
        return "ok";
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public DirectExchange analiseExchange() {
        return new DirectExchange(RabbitConstants.ANALISE_EXCHANGE);
    }

    @Bean
    public Queue analiseSolicitacaoQueue() {
        return new Queue(RabbitConstants.ANALISE_SOLICITACAO_QUEUE, true);
    }

    @Bean
    public Queue analiseCreditoSolicitarQueue() {
        return new Queue(RabbitConstants.ANALISE_CREDITO_SOLICITAR_QUEUE, true);
    }

    @Bean
    public Queue analiseFichaLimpaSolicitarQueue() {
        return new Queue(RabbitConstants.ANALISE_FICHA_LIMPA_SOLICITAR_QUEUE, true);
    }

    @Bean
    public Queue analiseStatusClienteSolicitarQueue() {
        return new Queue(RabbitConstants.ANALISE_STATUS_CLIENTE_SOLICITAR_QUEUE, true);
    }

    @Bean
    public Queue analiseCreditoResultadoQueue() {
        return new Queue(RabbitConstants.ANALISE_CREDITO_RESULTADO_QUEUE, true);
    }

    @Bean
    public Queue analiseFichaLimpaResultadoQueue() {
        return new Queue(RabbitConstants.ANALISE_FICHA_LIMPA_RESULTADO_QUEUE, true);
    }

    @Bean
    public Queue analiseStatusClienteResultadoQueue() {
        return new Queue(RabbitConstants.ANALISE_STATUS_CLIENTE_RESULTADO_QUEUE, true);
    }

    @Bean
    public Binding analiseSolicitacaoBinding() {
        return BindingBuilder
                .bind(analiseSolicitacaoQueue())
                .to(analiseExchange())
                .with(RabbitConstants.ANALISE_SOLICITACAO_ROUTING_KEY);
    }

    @Bean
    public Binding analiseCreditoSolicitarBinding() {
        return BindingBuilder
                .bind(analiseCreditoSolicitarQueue())
                .to(analiseExchange())
                .with(RabbitConstants.ANALISE_CREDITO_SOLICITAR_ROUTING_KEY);
    }

    @Bean
    public Binding analiseFichaLimpaSolicitarBinding() {
        return BindingBuilder
                .bind(analiseFichaLimpaSolicitarQueue())
                .to(analiseExchange())
                .with(RabbitConstants.ANALISE_FICHA_LIMPA_SOLICITAR_ROUTING_KEY);
    }

    @Bean
    public Binding analiseStatusClienteSolicitarBinding() {
        return BindingBuilder
                .bind(analiseStatusClienteSolicitarQueue())
                .to(analiseExchange())
                .with(RabbitConstants.ANALISE_STATUS_CLIENTE_SOLICITAR_ROUTING_KEY);
    }

    @Bean
    public Binding analiseCreditoResultadoBinding() {
        return BindingBuilder
                .bind(analiseCreditoResultadoQueue())
                .to(analiseExchange())
                .with(RabbitConstants.ANALISE_CREDITO_RESULTADO_ROUTING_KEY);
    }

    @Bean
    public Binding analiseFichaLimpaResultadoBinding() {
        return BindingBuilder
                .bind(analiseFichaLimpaResultadoQueue())
                .to(analiseExchange())
                .with(RabbitConstants.ANALISE_FICHA_LIMPA_RESULTADO_ROUTING_KEY);
    }

    @Bean
    public Binding analiseStatusClienteResultadoBinding() {
        return BindingBuilder
                .bind(analiseStatusClienteResultadoQueue())
                .to(analiseExchange())
                .with(RabbitConstants.ANALISE_STATUS_CLIENTE_RESULTADO_ROUTING_KEY);
    }
}