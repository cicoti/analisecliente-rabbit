package br.com.analisecliente.solicitacao.service;

import org.springframework.stereotype.Service;

import br.com.analisecliente.solicitacao.dto.ConsultarAnaliseClienteResponse;
import br.com.analisecliente.solicitacao.dto.ErroConsultaAnaliseClienteResponse;
import br.com.analisecliente.solicitacao.dto.ResultadoConsultaAnaliseClienteResponse;
import br.com.analisecliente.solicitacao.enums.StatusAnalise;
import br.com.analisecliente.solicitacao.exception.AnaliseNaoEncontradaException;
import br.com.analisecliente.solicitacao.model.ProcessoAnalise;
import br.com.analisecliente.solicitacao.model.ResultadoAnalise;
import br.com.analisecliente.solicitacao.repository.ProcessoAnaliseRepository;
import br.com.analisecliente.solicitacao.util.ResponseFactory;

@Service
public class ConsultarAnaliseClienteService {

    private final ProcessoAnaliseRepository processoAnaliseRepository;

    public ConsultarAnaliseClienteService(ProcessoAnaliseRepository processoAnaliseRepository) {
        this.processoAnaliseRepository = processoAnaliseRepository;
    }

    public ConsultarAnaliseClienteResponse consultarAnalise(String requestId) {
        ProcessoAnalise processo = processoAnaliseRepository.findById(requestId)
                .orElseThrow(() -> new AnaliseNaoEncontradaException("Solicitação não encontrada"));

        ConsultarAnaliseClienteResponse response = new ConsultarAnaliseClienteResponse();
        response.setRequestId(requestId);
        response.setStatusProcesso(processo.getStatus());

        if (StatusAnalise.PROCESSANDO.name().equals(processo.getStatus())) {
            response.setRetorno(ResponseFactory.criarRetornoSucesso("Solicitação em processamento"));
            return response;
        }

        if (StatusAnalise.ERRO.name().equals(processo.getStatus())) {
            if (processo.getErro() != null) {
                ErroConsultaAnaliseClienteResponse erroResponse = new ErroConsultaAnaliseClienteResponse();
                erroResponse.setOrigem(processo.getErro().getOrigem());
                erroResponse.setMensagem(processo.getErro().getMensagem());
                response.setErro(erroResponse);
            }

            response.setRetorno(ResponseFactory.criarRetornoErro(
                    -2,
                    "ERRO_NEGOCIO",
                    "Erro de negócio",
                    "Falha no processamento da solicitação"
            ));
            return response;
        }

        if (StatusAnalise.CONCLUIDO.name().equals(processo.getStatus())
                && processo.getResultado() != null
                && processo.getResultado().getResultadoConsolidado() != null) {
            response.setResultado(mapearResultado(processo.getResultado()));
            response.setRetorno(ResponseFactory.criarRetornoSucesso("Consulta realizada com sucesso"));
            return response;
        }

        response.setRetorno(ResponseFactory.criarRetornoErro(
                -1,
                "ERRO_SISTEMICO",
                "Erro sistêmico",
                "Estado do processo inválido"
        ));
        return response;
    }

    private ResultadoConsultaAnaliseClienteResponse mapearResultado(ResultadoAnalise resultado) {
        ResultadoConsultaAnaliseClienteResponse response = new ResultadoConsultaAnaliseClienteResponse();
        response.setVerificacaoCredito(resultado.getVerificacaoCredito());
        response.setVerificacaoFichaLimpa(resultado.getVerificacaoFichaLimpa());
        response.setVerificacaoStatusCliente(resultado.getVerificacaoStatusCliente());
        response.setResultadoConsolidado(resultado.getResultadoConsolidado());
        return response;
    }
}