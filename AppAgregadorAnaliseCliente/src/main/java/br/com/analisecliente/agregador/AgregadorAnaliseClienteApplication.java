package br.com.analisecliente.agregador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@EnableRabbit
@EnableScheduling
@SpringBootApplication
public class AgregadorAnaliseClienteApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgregadorAnaliseClienteApplication.class, args);
    }
}