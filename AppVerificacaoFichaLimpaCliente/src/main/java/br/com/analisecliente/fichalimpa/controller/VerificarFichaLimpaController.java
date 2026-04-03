package br.com.analisecliente.fichalimpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.analisecliente.fichalimpa.dto.ResponsePadrao;
import br.com.analisecliente.fichalimpa.dto.VerificarFichaLimpaRequest;
import br.com.analisecliente.fichalimpa.dto.VerificarFichaLimpaResponse;
import br.com.analisecliente.fichalimpa.service.VerificarFichaLimpaService;
import br.com.analisecliente.fichalimpa.util.ResponseFactory;

@RestController
@RequestMapping("/fichalimpa")
public class VerificarFichaLimpaController {

    @Autowired
    private VerificarFichaLimpaService verificarFichaLimpaService;

    @PostMapping("/verificar")
    public ResponsePadrao verificar(@RequestBody VerificarFichaLimpaRequest request) {
        VerificarFichaLimpaResponse dados = verificarFichaLimpaService.verificar(request);
        return ResponseFactory.sucesso(dados, "Consulta de ficha limpa realizada com sucesso");
    }
}