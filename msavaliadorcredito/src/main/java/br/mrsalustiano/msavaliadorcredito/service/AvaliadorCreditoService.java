package br.mrsalustiano.msavaliadorcredito.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.mrsalustiano.msavaliadorcredito.dto.Cartao;
import br.mrsalustiano.msavaliadorcredito.dto.CartaoAprovado;
import br.mrsalustiano.msavaliadorcredito.dto.CartaoCliente;
import br.mrsalustiano.msavaliadorcredito.dto.DadosCliente;
import br.mrsalustiano.msavaliadorcredito.dto.RetornoAvaliacaoCliente;
import br.mrsalustiano.msavaliadorcredito.dto.SituacaoCliente;
import br.mrsalustiano.msavaliadorcredito.exceptions.DadosClienteNotFoundException;
import br.mrsalustiano.msavaliadorcredito.exceptions.ErroComunicacaoMicroservicesExeception;
import br.mrsalustiano.msavaliadorcredito.service.clients.CartoesResourceClient;
import br.mrsalustiano.msavaliadorcredito.service.clients.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AvaliadorCreditoService {

	private final ClienteResourceClient clientResource;
	private final CartoesResourceClient cartoesResource;

	public SituacaoCliente obterSituacaoCliente(String cpf)
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesExeception {

		try {

			ResponseEntity<DadosCliente> responseCliente = clientResource.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> responseCartao = cartoesResource.getCartoesByCliente(cpf);

			return SituacaoCliente.builder().cliente(responseCliente.getBody()).cartoes(responseCartao.getBody())
					.build();
		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}

			throw new ErroComunicacaoMicroservicesExeception(e.getMessage(), status);

		}

	}

	public RetornoAvaliacaoCliente realizaAvaliacao(String cpf, Long renda)
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesExeception {

		try {
			ResponseEntity<DadosCliente> clienteResponse = clientResource.dadosCliente(cpf);
			ResponseEntity<List<Cartao>> cartoesResponse = cartoesResource.getCartoesRendaAte(renda);
			
			List<Cartao> cartoes = cartoesResponse.getBody();
			
			var listaCartoesAprovados =cartoes.stream().map(cartao -> {
				
				DadosCliente dadosCliente = clienteResponse.getBody();
				
				BigDecimal limiteBasico = cartao.getLimiteBasico();
				BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
				BigDecimal fator = idadeBD.divide(BigDecimal.valueOf(10));
				BigDecimal limiteAprovado = fator.multiply(limiteBasico);
				
				CartaoAprovado aprovado = new CartaoAprovado();
				aprovado.setCartao(cartao.getNome());
				aprovado.setBandeira(cartao.getBandeira());
				aprovado.setLimiteAprovado(limiteAprovado);
				
				return aprovado;
				
				
			}).collect(Collectors.toList());
			return new RetornoAvaliacaoCliente(listaCartoesAprovados);

		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}

			throw new ErroComunicacaoMicroservicesExeception(e.getMessage(), status);

		}
	}

}
