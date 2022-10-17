package br.mrsalustiano.msavaliadorcredito.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.mrsalustiano.msavaliadorcredito.dto.CartaoCliente;
import br.mrsalustiano.msavaliadorcredito.dto.DadosCliente;
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
	
	
	public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException,ErroComunicacaoMicroservicesExeception {
		
		try {
		
		ResponseEntity<DadosCliente> responseCliente = clientResource.dadosCliente(cpf);
		ResponseEntity<List<CartaoCliente>> responseCartao = cartoesResource.getCartoesByCliente(cpf);
		
		
		return SituacaoCliente
				.builder()
				.cliente(responseCliente.getBody())
				.cartoes(responseCartao.getBody())
				.build();
		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}
			
			throw new ErroComunicacaoMicroservicesExeception(e.getMessage(), status);
			
		}
		
	}
	
}
