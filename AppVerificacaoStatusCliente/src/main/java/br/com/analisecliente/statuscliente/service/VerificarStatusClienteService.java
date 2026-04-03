package br.com.analisecliente.statuscliente.service;

import org.springframework.stereotype.Service;

import br.com.analisecliente.statuscliente.dto.VerificarStatusClienteRequest;
import br.com.analisecliente.statuscliente.dto.VerificarStatusClienteResponse;
import br.com.analisecliente.statuscliente.exception.RequisicaoInvalidaException;

@Service
public class VerificarStatusClienteService {

    public VerificarStatusClienteResponse verificar(VerificarStatusClienteRequest request) {
        if (request == null || request.getCpf() == null || request.getCpf().trim().isEmpty()) {
            throw new RequisicaoInvalidaException("CPF é obrigatório");
        }

        VerificarStatusClienteResponse response = new VerificarStatusClienteResponse();
        response.setCpf(request.getCpf());

        if (request.getCpf().endsWith("3")) {
            response.setClienteAtivo(false);
            response.setMensagem("Cliente inativo");
        } else {
            response.setClienteAtivo(true);
            response.setMensagem("Cliente ativo");
        }

        return response;
    }
}