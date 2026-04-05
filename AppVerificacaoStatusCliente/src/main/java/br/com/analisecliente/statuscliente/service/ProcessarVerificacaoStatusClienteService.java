package br.com.analisecliente.statuscliente.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.analisecliente.statuscliente.dto.ResultadoVerificacaoStatusClienteMessage;
import br.com.analisecliente.statuscliente.dto.SolicitarVerificacaoStatusClienteMessage;

@Service
public class ProcessarVerificacaoStatusClienteService {

    public ResultadoVerificacaoStatusClienteMessage executar(SolicitarVerificacaoStatusClienteMessage message) {
        ResultadoVerificacaoStatusClienteMessage resultado = new ResultadoVerificacaoStatusClienteMessage();
        resultado.setRequestId(message.getRequestId());
        resultado.setCpf(message.getCpf());
        resultado.setClienteAtivo(clienteAtivoMock(message.getCpf()));
        resultado.setMensagem(criarMensagem(resultado.getClienteAtivo()));
        resultado.setDataHoraProcessamento(LocalDateTime.now());
        return resultado;
    }

    private Boolean clienteAtivoMock(String cpf) {
        char ultimoDigito = cpf.charAt(cpf.length() - 1);
        int numero = Character.getNumericValue(ultimoDigito);
        return numero % 2 == 0;
    }

    private String criarMensagem(Boolean clienteAtivo) {
        if (Boolean.TRUE.equals(clienteAtivo)) {
            return "Cliente está ativo";
        }
        return "Cliente está inativo";
    }
}