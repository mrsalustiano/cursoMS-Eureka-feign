package br.mrsalustiano.mscartoes.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.mrsalustiano.mscartoes.entity.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

	List<Cartao> findByRendaLessThanEqual(BigDecimal rendaBigDecimal);

}
