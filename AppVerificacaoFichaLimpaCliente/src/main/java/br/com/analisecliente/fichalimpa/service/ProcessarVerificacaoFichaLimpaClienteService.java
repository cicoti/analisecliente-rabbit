package br.com.analisecliente.fichalimpa.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.analisecliente.fichalimpa.dto.ResultadoVerificacaoFichaLimpaMessage;
import br.com.analisecliente.fichalimpa.dto.SolicitarVerificacaoFichaLimpaMessage;

@Service
public class ProcessarVerificacaoFichaLimpaClienteService {

    public ResultadoVerificacaoFichaLimpaMessage executar(SolicitarVerificacaoFichaLimpaMessage message) {
        ResultadoVerificacaoFichaLimpaMessage resultado = new ResultadoVerificacaoFichaLimpaMessage();
        resultado.setRequestId(message.getRequestId());
        resultado.setCpf(message.getCpf());
        resultado.setPossuiFichaLimpa(possuiFichaLimpaMock(message.getCpf()));
        resultado.setMensagem(criarMensagem(resultado.getPossuiFichaLimpa()));
        resultado.setDataHoraProcessamento(LocalDateTime.now());
        return resultado;
    }

    private Boolean possuiFichaLimpaMock(String cpf) {
        char ultimoDigito = cpf.charAt(cpf.length() - 1);
        int numero = Character.getNumericValue(ultimoDigito);
        return numero % 2 == 0;
    }

    private String criarMensagem(Boolean possuiFichaLimpa) {
        if (Boolean.TRUE.equals(possuiFichaLimpa)) {
            return "Cliente possui ficha limpa";
        }
        return "Cliente não possui ficha limpa";
    }
}