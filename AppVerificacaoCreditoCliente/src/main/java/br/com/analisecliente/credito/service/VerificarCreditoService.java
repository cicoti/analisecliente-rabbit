package br.com.analisecliente.credito.service;

import org.springframework.stereotype.Service;

import br.com.analisecliente.credito.dto.VerificarCreditoRequest;
import br.com.analisecliente.credito.dto.VerificarCreditoResponse;
import br.com.analisecliente.credito.exception.RequisicaoInvalidaException;

@Service
public class VerificarCreditoService {

    public VerificarCreditoResponse verificar(VerificarCreditoRequest request) {
        if (request == null || request.getCpf() == null || request.getCpf().trim().isEmpty()) {
            throw new RequisicaoInvalidaException("CPF é obrigatório");
        }

        VerificarCreditoResponse response = new VerificarCreditoResponse();
        response.setCpf(request.getCpf());

        if (request.getCpf().endsWith("1")) {
            response.setPossuiCredito(false);
            response.setMensagem("Cliente sem crédito disponível");
        } else {
            response.setPossuiCredito(true);
            response.setMensagem("Cliente com crédito disponível");
        }

        return response;
    }
}