package br.com.analisecliente.agregador.client;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import br.com.analisecliente.agregador.dto.VerificacaoCreditoDados;
import br.com.analisecliente.agregador.dto.VerificarClienteRequest;

@Component
public class CreditoClient {

    private final RestClient restClient;

    public CreditoClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @SuppressWarnings("unchecked")
    public VerificacaoCreditoDados verificar(String cpf) {
    	VerificarClienteRequest  request = new VerificarClienteRequest ();
        request.setCpf(cpf);

        Map<String, Object> response = restClient.post()
                .uri("http://localhost:8081/credito/verificar")
                .body(request)
                .retrieve()
                .body(Map.class);

        Map<String, Object> dadosMap = (Map<String, Object>) response.get("dados");

        VerificacaoCreditoDados dados = new VerificacaoCreditoDados();
        dados.setCpf((String) dadosMap.get("cpf"));
        dados.setMensagem((String) dadosMap.get("mensagem"));
        dados.setPossuiCredito((Boolean) dadosMap.get("possuiCredito"));
        return dados;
    }
}