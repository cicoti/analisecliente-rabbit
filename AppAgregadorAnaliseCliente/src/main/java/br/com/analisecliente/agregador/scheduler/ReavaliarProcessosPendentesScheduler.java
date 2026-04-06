package br.com.analisecliente.agregador.scheduler;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.analisecliente.agregador.enums.StatusAnalise;
import br.com.analisecliente.agregador.model.ProcessoAnalise;
import br.com.analisecliente.agregador.repository.ProcessoAnaliseRepository;
import br.com.analisecliente.agregador.service.ConsolidarResultadoAnaliseService;

@Component
public class ReavaliarProcessosPendentesScheduler {

    private final ProcessoAnaliseRepository processoAnaliseRepository;
    private final ConsolidarResultadoAnaliseService consolidarResultadoAnaliseService;

    public ReavaliarProcessosPendentesScheduler(
            ProcessoAnaliseRepository processoAnaliseRepository,
            ConsolidarResultadoAnaliseService consolidarResultadoAnaliseService) {
        this.processoAnaliseRepository = processoAnaliseRepository;
        this.consolidarResultadoAnaliseService = consolidarResultadoAnaliseService;
    }

    @Scheduled(fixedDelay = 1000)
    public void executar() {
        List<ProcessoAnalise> processosPendentes =
                processoAnaliseRepository.findByStatus(StatusAnalise.PROCESSANDO.name());

        for (ProcessoAnalise processo : processosPendentes) {
            try {
                consolidarResultadoAnaliseService.executar(processo.getRequestId());
            } catch (Exception ex) {
                System.out.println("Falha ao reavaliar processo pendente. requestId="
                        + processo.getRequestId() + ", erro=" + ex.getMessage());
            }
        }
    }
}