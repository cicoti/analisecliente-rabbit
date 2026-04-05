package br.com.analisecliente.agregador.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.dto.ResultadoVerificacaoFichaLimpaMessage;
import br.com.analisecliente.agregador.model.ProcessoAnalise;

@Service
public class AtualizarResultadoVerificacaoFichaLimpaService {

    private final MongoTemplate mongoTemplate;
    private final ConsolidarResultadoAnaliseService consolidarResultadoAnaliseService;

    public AtualizarResultadoVerificacaoFichaLimpaService(
            MongoTemplate mongoTemplate,
            ConsolidarResultadoAnaliseService consolidarResultadoAnaliseService) {
        this.mongoTemplate = mongoTemplate;
        this.consolidarResultadoAnaliseService = consolidarResultadoAnaliseService;
    }

    public void executar(ResultadoVerificacaoFichaLimpaMessage message) {
        Map<String, Object> verificacaoFichaLimpa = new HashMap<String, Object>();
        verificacaoFichaLimpa.put("possuiFichaLimpa", message.getPossuiFichaLimpa());
        verificacaoFichaLimpa.put("mensagem", message.getMensagem());
        verificacaoFichaLimpa.put("dataHoraProcessamento", message.getDataHoraProcessamento());

        Query query = Query.query(Criteria.where("_id").is(message.getRequestId()));

        Update update = new Update();
        update.set("resultado.verificacaoFichaLimpa", verificacaoFichaLimpa);
        update.set("dataHoraAtualizacao", LocalDateTime.now());

        mongoTemplate.updateFirst(query, update, ProcessoAnalise.class);

        System.out.println("Resultado de ficha limpa gravado no Mongo. requestId=" + message.getRequestId());

        consolidarResultadoAnaliseService.executar(message.getRequestId());
    }
}