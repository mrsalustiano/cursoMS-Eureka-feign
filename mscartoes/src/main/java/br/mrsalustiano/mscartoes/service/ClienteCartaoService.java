package br.mrsalustiano.mscartoes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.mrsalustiano.mscartoes.entity.ClienteCartao;
import br.mrsalustiano.mscartoes.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
	
	private final ClienteCartaoRepository repository;
	
	
	public List<ClienteCartao> listCartoesByCpf(String cpf){
		return repository.findByCpf(cpf);
	}

	
}
