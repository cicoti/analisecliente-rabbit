package br.com.analisecliente.credito.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.analisecliente.credito.dto.ResponsePadrao;
import br.com.analisecliente.credito.dto.VerificarCreditoRequest;
import br.com.analisecliente.credito.dto.VerificarCreditoResponse;
import br.com.analisecliente.credito.service.VerificarCreditoService;
import br.com.analisecliente.credito.util.ResponseFactory;

@RestController
@RequestMapping("/credito")
public class VerificarCreditoController {

    @Autowired
    private VerificarCreditoService verificarCreditoService;

    @PostMapping("/verificar")
    public ResponsePadrao verificar(@RequestBody VerificarCreditoRequest request) {
        VerificarCreditoResponse dados = verificarCreditoService.verificar(request);
        return ResponseFactory.sucesso(dados, "Consulta de crédito realizada com sucesso");
    }
}