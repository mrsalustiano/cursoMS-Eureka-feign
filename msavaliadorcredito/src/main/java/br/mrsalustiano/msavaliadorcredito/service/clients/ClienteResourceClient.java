package br.mrsalustiano.msavaliadorcredito.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.mrsalustiano.msavaliadorcredito.dto.DadosCliente;

@FeignClient(value = "mscartoes" , path= "/cartoes")
public interface ClienteResourceClient {
	
	@GetMapping(params = "cpf")
	ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);
	
	
	

}
