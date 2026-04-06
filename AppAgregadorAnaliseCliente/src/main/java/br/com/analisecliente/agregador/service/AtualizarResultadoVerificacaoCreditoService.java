package br.com.analisecliente.agregador.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import br.com.analisecliente.agregador.dto.ResultadoVerificacaoCreditoMessage;
import br.com.analisecliente.agregador.enums.StatusAnalise;
import br.com.analisecliente.agregador.model.ProcessoAnalise;
import com.mongodb.client.result.UpdateResult;

@Service
public class AtualizarResultadoVerificacaoCreditoService {

    private final MongoTemplate mongoTemplate;
    private final ConsolidarResultadoAnaliseService consolidarResultadoAnaliseService;

    public AtualizarResultadoVerificacaoCreditoService(
            MongoTemplate mongoTemplate,
            ConsolidarResultadoAnaliseService consolidarResultadoAnaliseService) {
        this.mongoTemplate = mongoTemplate;
        this.consolidarResultadoAnaliseService = consolidarResultadoAnaliseService;
    }

    public void executar(ResultadoVerificacaoCreditoMessage message) {
        Map<String, Object> verificacaoCredito = new HashMap<String, Object>();
        verificacaoCredito.put("possuiCredito", message.getPossuiCredito());
        verificacaoCredito.put("mensagem", message.getMensagem());
        verificacaoCredito.put("dataHoraProcessamento", message.getDataHoraProcessamento());

        Query query = Query.query(
                Criteria.where("_id").is(message.getRequestId())
                        .and("status").is(StatusAnalise.PROCESSANDO.name())
        );

        Update update = new Update();
        update.set("resultado.verificacaoCredito", verificacaoCredito);
        update.set("dataHoraAtualizacao", LocalDateTime.now());

        UpdateResult resultadoUpdate = mongoTemplate.updateFirst(query, update, ProcessoAnalise.class);

        if (resultadoUpdate.getMatchedCount() == 0) {
            System.out.println("Resultado de crédito ignorado. Processo não está mais em PROCESSANDO. requestId="
                    + message.getRequestId());
            return;
        }

        System.out.println("Resultado de crédito gravado no Mongo. requestId=" + message.getRequestId());

        consolidarResultadoAnaliseService.executar(message.getRequestId());
    }
}