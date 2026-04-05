package br.com.analisecliente.credito.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.analisecliente.credito.dto.ResultadoVerificacaoCreditoMessage;
import br.com.analisecliente.credito.dto.SolicitarVerificacaoCreditoMessage;

@Service
public class ProcessarVerificacaoCreditoClienteService {

    public ResultadoVerificacaoCreditoMessage executar(SolicitarVerificacaoCreditoMessage message) {
        ResultadoVerificacaoCreditoMessage resultado = new ResultadoVerificacaoCreditoMessage();
        resultado.setRequestId(message.getRequestId());
        resultado.setCpf(message.getCpf());
        resultado.setPossuiCredito(possuiCreditoMock(message.getCpf()));
        resultado.setMensagem(criarMensagem(resultado.getPossuiCredito()));
        resultado.setDataHoraProcessamento(LocalDateTime.now());
        return resultado;
    }

    private Boolean possuiCreditoMock(String cpf) {
        char ultimoDigito = cpf.charAt(cpf.length() - 1);
        int numero = Character.getNumericValue(ultimoDigito);
        return numero % 2 == 0;
    }

    private String criarMensagem(Boolean possuiCredito) {
        if (Boolean.TRUE.equals(possuiCredito)) {
            return "Cliente possui crédito";
        }
        return "Cliente não possui crédito";
    }
}