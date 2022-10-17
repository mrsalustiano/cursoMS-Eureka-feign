package br.mrsalustiano.mscartoes.dto;

import java.math.BigDecimal;

import br.mrsalustiano.mscartoes.entity.BandeiraCartao;
import br.mrsalustiano.mscartoes.entity.Cartao;
import lombok.Data;

@Data
public class CartaoSaveRequest {
	
	private String nome;
	private BigDecimal renda;
	private BandeiraCartao bandeira;
	private BigDecimal limiteBasico;
	
	public Cartao toModel() {
		return new Cartao(nome,bandeira,renda,limiteBasico);
	}

}
