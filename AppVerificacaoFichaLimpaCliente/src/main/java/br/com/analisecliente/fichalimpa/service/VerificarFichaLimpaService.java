package br.com.analisecliente.fichalimpa.service;

import org.springframework.stereotype.Service;

import br.com.analisecliente.fichalimpa.dto.VerificarFichaLimpaRequest;
import br.com.analisecliente.fichalimpa.dto.VerificarFichaLimpaResponse;
import br.com.analisecliente.fichalimpa.exception.RequisicaoInvalidaException;

@Service
public class VerificarFichaLimpaService {

    public VerificarFichaLimpaResponse verificar(VerificarFichaLimpaRequest request) {
        if (request == null || request.getCpf() == null || request.getCpf().trim().isEmpty()) {
            throw new RequisicaoInvalidaException("CPF é obrigatório");
        }

        VerificarFichaLimpaResponse response = new VerificarFichaLimpaResponse();
        response.setCpf(request.getCpf());

        if (request.getCpf().endsWith("2")) {
            response.setPossuiFichaLimpa(false);
            response.setMensagem("Cliente possui restrições");
        } else {
            response.setPossuiFichaLimpa(true);
            response.setMensagem("Cliente sem restrições");
        }

        return response;
    }
}