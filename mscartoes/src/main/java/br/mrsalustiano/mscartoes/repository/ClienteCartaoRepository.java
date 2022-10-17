package br.mrsalustiano.mscartoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mrsalustiano.mscartoes.entity.ClienteCartao;

public interface ClienteCartaoRepository extends JpaRepository<ClienteCartao, Long> {
	
	List<ClienteCartao> findByCpf(String cpf);

}
