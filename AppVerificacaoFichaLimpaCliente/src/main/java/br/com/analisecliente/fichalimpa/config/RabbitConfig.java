package br.com.analisecliente.fichalimpa.config;

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

    public static final String FICHA_LIMPA_SOLICITAR_QUEUE = "analise.ficha-limpa.solicitar.queue";
    public static final String FICHA_LIMPA_RESULTADO_QUEUE = "analise.ficha-limpa.resultado.queue";

    public static final String FICHA_LIMPA_SOLICITAR_ROUTING_KEY = "analise.ficha-limpa.solicitar";
    public static final String FICHA_LIMPA_RESULTADO_ROUTING_KEY = "analise.ficha-limpa.resultado";

    @Bean
    public DirectExchange analiseExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue fichaLimpaSolicitarQueue() {
        return new Queue(FICHA_LIMPA_SOLICITAR_QUEUE, true);
    }

    @Bean
    public Queue fichaLimpaResultadoQueue() {
        return new Queue(FICHA_LIMPA_RESULTADO_QUEUE, true);
    }

    @Bean
    public Binding fichaLimpaSolicitarBinding(Queue fichaLimpaSolicitarQueue, DirectExchange analiseExchange) {
        return BindingBuilder.bind(fichaLimpaSolicitarQueue).to(analiseExchange).with(FICHA_LIMPA_SOLICITAR_ROUTING_KEY);
    }

    @Bean
    public Binding fichaLimpaResultadoBinding(Queue fichaLimpaResultadoQueue, DirectExchange analiseExchange) {
        return BindingBuilder.bind(fichaLimpaResultadoQueue).to(analiseExchange).with(FICHA_LIMPA_RESULTADO_ROUTING_KEY);
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