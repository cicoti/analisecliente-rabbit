package br.com.analisecliente.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.analisecliente.dto.ConsultarAnaliseClienteResponse;
import br.com.analisecliente.dto.ResponsePadrao;
import br.com.analisecliente.dto.SolicitarAnaliseClienteRequest;
import br.com.analisecliente.service.ConsultarAnaliseClienteService;
import br.com.analisecliente.service.SolicitarAnaliseClienteService;

@RestController
@RequestMapping("/analises")
public class AnaliseClienteController {

    private final SolicitarAnaliseClienteService solicitarAnaliseClienteService;
    private final ConsultarAnaliseClienteService consultarAnaliseClienteService;

    public AnaliseClienteController(SolicitarAnaliseClienteService solicitarAnaliseClienteService,
                                    ConsultarAnaliseClienteService consultarAnaliseClienteService) {
        this.solicitarAnaliseClienteService = solicitarAnaliseClienteService;
        this.consultarAnaliseClienteService = consultarAnaliseClienteService;
    }

    @PostMapping
    public ResponseEntity<ResponsePadrao> solicitarAnalise(
            @RequestBody SolicitarAnaliseClienteRequest request) {

        ResponsePadrao response = solicitarAnaliseClienteService.solicitar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ConsultarAnaliseClienteResponse> consultarAnalise(
            @PathVariable String requestId) {

        ConsultarAnaliseClienteResponse response = consultarAnaliseClienteService.consultar(requestId);
        return ResponseEntity.ok(response);
    }
}