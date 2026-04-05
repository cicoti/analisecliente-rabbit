package br.com.analisecliente.statuscliente.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "analise.exchange";

    public static final String STATUS_CLIENTE_SOLICITAR_QUEUE = "analise.status-cliente.solicitar.queue";
    public static final String STATUS_CLIENTE_RESULTADO_QUEUE = "analise.status-cliente.resultado.queue";

    public static final String STATUS_CLIENTE_SOLICITAR_ROUTING_KEY = "analise.status-cliente.solicitar";
    public static final String STATUS_CLIENTE_RESULTADO_ROUTING_KEY = "analise.status-cliente.resultado";

    @Bean
    public DirectExchange analiseExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue statusClienteSolicitarQueue() {
        return new Queue(STATUS_CLIENTE_SOLICITAR_QUEUE, true);
    }

    @Bean
    public Queue statusClienteResultadoQueue() {
        return new Queue(STATUS_CLIENTE_RESULTADO_QUEUE, true);
    }

    @Bean
    public Binding statusClienteSolicitarBinding(Queue statusClienteSolicitarQueue, DirectExchange analiseExchange) {
        return BindingBuilder.bind(statusClienteSolicitarQueue).to(analiseExchange).with(STATUS_CLIENTE_SOLICITAR_ROUTING_KEY);
    }

    @Bean
    public Binding statusClienteResultadoBinding(Queue statusClienteResultadoQueue, DirectExchange analiseExchange) {
        return BindingBuilder.bind(statusClienteResultadoQueue).to(analiseExchange).with(STATUS_CLIENTE_RESULTADO_ROUTING_KEY);
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationRunner applicationRunner(RabbitAdmin rabbitAdmin) {
        return args -> rabbitAdmin.initialize();
    }
}