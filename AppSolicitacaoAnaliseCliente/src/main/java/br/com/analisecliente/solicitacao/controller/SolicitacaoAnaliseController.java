package br.com.analisecliente.solicitacao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.analisecliente.solicitacao.dto.ConsultarAnaliseClienteResponse;
import br.com.analisecliente.solicitacao.dto.ResponsePadrao;
import br.com.analisecliente.solicitacao.dto.SolicitarAnaliseClienteRequest;
import br.com.analisecliente.solicitacao.service.ConsultarAnaliseClienteService;
import br.com.analisecliente.solicitacao.service.SolicitarAnaliseClienteService;

@RestController
@RequestMapping("/analises")
public class SolicitacaoAnaliseController {

    private final SolicitarAnaliseClienteService solicitarAnaliseClienteService;
    private final ConsultarAnaliseClienteService consultarAnaliseClienteService;

    public SolicitacaoAnaliseController(SolicitarAnaliseClienteService solicitarAnaliseClienteService,
                                        ConsultarAnaliseClienteService consultarAnaliseClienteService) {
        this.solicitarAnaliseClienteService = solicitarAnaliseClienteService;
        this.consultarAnaliseClienteService = consultarAnaliseClienteService;
    }

    @PostMapping
    public ResponseEntity<ResponsePadrao> solicitarAnalise(@RequestBody SolicitarAnaliseClienteRequest request) {
        return ResponseEntity.ok(solicitarAnaliseClienteService.solicitarAnalise(request));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ConsultarAnaliseClienteResponse> consultarAnalise(@PathVariable String requestId) {
        return ResponseEntity.ok(consultarAnaliseClienteService.consultarAnalise(requestId));
    }
}