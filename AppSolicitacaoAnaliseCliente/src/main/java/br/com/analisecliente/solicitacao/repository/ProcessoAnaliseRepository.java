package br.com.analisecliente.solicitacao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.analisecliente.solicitacao.model.ProcessoAnalise;

@Repository
public interface ProcessoAnaliseRepository extends MongoRepository<ProcessoAnalise, String> {

}