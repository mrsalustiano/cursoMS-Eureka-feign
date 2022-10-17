package br.mrsalustiano.msavaliadorcredito.service.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.mrsalustiano.msavaliadorcredito.dto.CartaoCliente;



@FeignClient(value = "mscartoes" , path= "/clientes")
public interface CartoesResourceClient {
	
	
	@GetMapping(params = "cpf")
	ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);
		

}
