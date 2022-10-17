package br.mrsalustiano.mscartoes.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.mrsalustiano.mscartoes.dto.CartaoSaveRequest;
import br.mrsalustiano.mscartoes.dto.CartoesPorClienteResponse;
import br.mrsalustiano.mscartoes.entity.Cartao;
import br.mrsalustiano.mscartoes.entity.ClienteCartao;
import br.mrsalustiano.mscartoes.service.CartaoService;
import br.mrsalustiano.mscartoes.service.ClienteCartaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
@Slf4j
public class CartoesController {
	
	private final CartaoService cartaoService;
	private final ClienteCartaoService clienteService;
	
		
		@GetMapping
		public String Status() {
			log.info("Obtendo Status do MS de cartoes");
			return "OK";
		}
		
		
		@PostMapping
		public ResponseEntity cadastrar(@RequestBody CartaoSaveRequest request) {
			Cartao cartao  = request.toModel();
			cartaoService.save(cartao);
			return ResponseEntity.status(HttpStatus.CREATED).build();			
		}
		
		@GetMapping(params = "renda")
		public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda) {
			
			List<Cartao> listCartao = cartaoService.getCartaoRendaMenorIgual(renda);			
			return ResponseEntity.ok(listCartao);

		}
		
		@GetMapping(params = "cpf")
		public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf){
			
			List<ClienteCartao> listClienteCartao = clienteService.listCartoesByCpf(cpf);
			List<CartoesPorClienteResponse> resultList = listClienteCartao
															.stream()
															.map(CartoesPorClienteResponse::fromModel)
															.collect(Collectors.toList());
			return ResponseEntity.ok(resultList);
			
		}
		
}
