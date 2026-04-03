package br.com.analisecliente.agregador.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.analisecliente.agregador.model.ProcessoAnalise;

public interface ProcessoAnaliseRepository extends MongoRepository<ProcessoAnalise, String> {
}