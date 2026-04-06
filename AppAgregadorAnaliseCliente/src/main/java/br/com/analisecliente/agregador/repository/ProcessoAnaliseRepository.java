package br.com.analisecliente.agregador.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.analisecliente.agregador.model.ProcessoAnalise;

@Repository
public interface ProcessoAnaliseRepository extends MongoRepository<ProcessoAnalise, String> {

    Optional<ProcessoAnalise> findByRequestId(String requestId);

    List<ProcessoAnalise> findByStatus(String status);
}