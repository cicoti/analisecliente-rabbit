package br.com.analisecliente.agregador.client;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import br.com.analisecliente.agregador.dto.VerificacaoFichaLimpaDados;
import br.com.analisecliente.agregador.dto.VerificarClienteRequest;

@Component
public class FichaLimpaClient {

    private final RestClient restClient;

    public FichaLimpaClient(RestClient restClient) {
        this.restClient = restClient;
    }

    @SuppressWarnings("unchecked")
    public VerificacaoFichaLimpaDados verificar(String cpf) {
    	VerificarClienteRequest  request = new VerificarClienteRequest ();
        request.setCpf(cpf);

        Map<String, Object> response = restClient.post()
                .uri("http://localhost:8082/fichalimpa/verificar")
                .body(request)
                .retrieve()
                .body(Map.class);

        Map<String, Object> dadosMap = (Map<String, Object>) response.get("dados");

        VerificacaoFichaLimpaDados dados = new VerificacaoFichaLimpaDados();
        dados.setCpf((String) dadosMap.get("cpf"));
        dados.setMensagem((String) dadosMap.get("mensagem"));
        dados.setPossuiFichaLimpa((Boolean) dadosMap.get("possuiFichaLimpa"));
        return dados;
    }
}