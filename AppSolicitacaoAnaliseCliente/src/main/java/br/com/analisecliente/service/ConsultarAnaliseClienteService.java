package br.com.analisecliente.service;

import org.springframework.stereotype.Service;

import br.com.analisecliente.dto.ConsultarAnaliseClienteResponse;
import br.com.analisecliente.dto.ErroConsultaAnaliseClienteResponse;
import br.com.analisecliente.dto.ResultadoConsultaAnaliseClienteResponse;
import br.com.analisecliente.enums.StatusAnalise;
import br.com.analisecliente.exception.AnaliseNaoEncontradaException;
import br.com.analisecliente.model.ProcessoAnalise;
import br.com.analisecliente.model.ResultadoAnalise;
import br.com.analisecliente.repository.ProcessoAnaliseRepository;
import br.com.analisecliente.util.ResponseFactory;

@Service
public class ConsultarAnaliseClienteService {

    private final ProcessoAnaliseRepository processoAnaliseRepository;

    public ConsultarAnaliseClienteService(ProcessoAnaliseRepository processoAnaliseRepository) {
        this.processoAnaliseRepository = processoAnaliseRepository;
    }

    public ConsultarAnaliseClienteResponse consultar(String requestId) {
        ProcessoAnalise processo = processoAnaliseRepository.findById(requestId)
                .orElseThrow(() -> new AnaliseNaoEncontradaException("Solicitação não encontrada"));

        ConsultarAnaliseClienteResponse response = new ConsultarAnaliseClienteResponse();
        response.setRequestId(requestId);
        response.setStatusProcesso(processo.getStatus().name());

        if (StatusAnalise.PROCESSANDO.equals(processo.getStatus())) {
            response.setRetorno(ResponseFactory.retornoSucesso("Solicitação em processamento"));
            return response;
        }

        if (StatusAnalise.ERRO.equals(processo.getStatus())) {
            if (processo.getErro() != null) {
                ErroConsultaAnaliseClienteResponse erroResponse = new ErroConsultaAnaliseClienteResponse();
                erroResponse.setOrigem(processo.getErro().getOrigem());
                erroResponse.setMensagem(processo.getErro().getMensagem());
                response.setErro(erroResponse);
            }

            response.setRetorno(ResponseFactory.retornoErro(
                    -2,
                    "ERRO_NEGOCIO",
                    "Erro de negócio",
                    "Falha no processamento da solicitação"
            ));
            return response;
        }

        if (StatusAnalise.CONCLUIDO.equals(processo.getStatus()) && processo.getResultado() != null) {
            response.setResultado(mapearResultado(processo.getResultado()));
            response.setRetorno(ResponseFactory.retornoSucesso("Consulta realizada com sucesso"));
            return response;
        }

        response.setRetorno(ResponseFactory.retornoErro(
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