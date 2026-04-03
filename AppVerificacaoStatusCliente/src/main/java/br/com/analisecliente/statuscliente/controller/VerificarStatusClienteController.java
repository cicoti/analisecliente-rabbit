package br.com.analisecliente.statuscliente.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.analisecliente.statuscliente.dto.ResponsePadrao;
import br.com.analisecliente.statuscliente.dto.VerificarStatusClienteRequest;
import br.com.analisecliente.statuscliente.dto.VerificarStatusClienteResponse;
import br.com.analisecliente.statuscliente.service.VerificarStatusClienteService;
import br.com.analisecliente.statuscliente.util.ResponseFactory;

@RestController
@RequestMapping("/statuscliente")
public class VerificarStatusClienteController {

    @Autowired
    private VerificarStatusClienteService verificarStatusClienteService;

    @PostMapping("/verificar")
    public ResponsePadrao verificar(@RequestBody VerificarStatusClienteRequest request) {
        VerificarStatusClienteResponse dados = verificarStatusClienteService.verificar(request);
        return ResponseFactory.sucesso(dados, "Consulta de status do cliente realizada com sucesso");
    }
}