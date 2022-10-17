package br.mrsalustiano.msavaliadorcredito.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.mrsalustiano.msavaliadorcredito.dto.SituacaoCliente;
import br.mrsalustiano.msavaliadorcredito.exceptions.DadosClienteNotFoundException;
import br.mrsalustiano.msavaliadorcredito.exceptions.ErroComunicacaoMicroservicesExeception;
import br.mrsalustiano.msavaliadorcredito.service.AvaliadorCreditoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

	
	private final  AvaliadorCreditoService avaliadorCreditoService;
	

	@GetMapping
	public String Status() {
		return "OK";
	}
	
	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf){
		
		try {
			SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();

		} catch (ErroComunicacaoMicroservicesExeception e) {
			
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage() );
		}
		
	}
		
		
		
		
	
 }
