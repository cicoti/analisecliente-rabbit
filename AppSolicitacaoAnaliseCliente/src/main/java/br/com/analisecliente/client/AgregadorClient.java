package br.com.analisecliente.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import br.com.analisecliente.dto.ProcessarAnaliseRequest;

@Component
public class AgregadorClient {

    private final RestClient restClient;

    public AgregadorClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public void processar(String requestId, String cpf) {
        ProcessarAnaliseRequest request = new ProcessarAnaliseRequest();
        request.setRequestId(requestId);
        request.setCpf(cpf);

        restClient.post()
                .uri("http://localhost:8084/agregador/processar")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }
}