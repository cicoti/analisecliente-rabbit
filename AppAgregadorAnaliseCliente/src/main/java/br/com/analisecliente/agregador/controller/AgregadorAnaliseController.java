package br.com.analisecliente.agregador.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.analisecliente.agregador.dto.ProcessarAnaliseRequest;
import br.com.analisecliente.agregador.dto.ResponsePadrao;
import br.com.analisecliente.agregador.service.AgregadorAnaliseService;

@RestController
@RequestMapping("/agregador")
public class AgregadorAnaliseController {

    private final AgregadorAnaliseService agregadorAnaliseService;

    public AgregadorAnaliseController(AgregadorAnaliseService agregadorAnaliseService) {
        this.agregadorAnaliseService = agregadorAnaliseService;
    }

    @PostMapping("/processar")
    public ResponsePadrao processar(@RequestBody ProcessarAnaliseRequest request) {
        return agregadorAnaliseService.processar(request);
    }
}