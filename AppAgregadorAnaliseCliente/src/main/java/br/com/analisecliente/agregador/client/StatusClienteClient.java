package br.com.analisecliente.agregador.client;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import br.com.analisecliente.agregador.dto.VerificacaoStatusClienteDados;
import br.com.analisecliente.agregador.dto.VerificarClienteRequest;

@Component
public class StatusClienteClient {

    private final RestClient restClient;

    public StatusClienteClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @SuppressWarnings("unchecked")
    public VerificacaoStatusClienteDados verificar(String cpf) {
    	VerificarClienteRequest  request = new VerificarClienteRequest ();
        request.setCpf(cpf);

        Map<String, Object> response = restClient.post()
                .uri("http://localhost:8083/statuscliente/verificar")
                .body(request)
                .retrieve()
                .body(Map.class);

        Map<String, Object> dadosMap = (Map<String, Object>) response.get("dados");

        VerificacaoStatusClienteDados dados = new VerificacaoStatusClienteDados();
        dados.setCpf((String) dadosMap.get("cpf"));
        dados.setMensagem((String) dadosMap.get("mensagem"));
        dados.setClienteAtivo((Boolean) dadosMap.get("clienteAtivo"));
        return dados;
    }
}