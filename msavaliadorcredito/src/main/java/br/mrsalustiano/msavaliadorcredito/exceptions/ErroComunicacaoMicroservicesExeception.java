package br.mrsalustiano.msavaliadorcredito.exceptions;

import lombok.Getter;

public class ErroComunicacaoMicroservicesExeception extends Exception{

	@Getter
	private Integer status;
	
	
	public ErroComunicacaoMicroservicesExeception(String msgErro, Integer status) {
		super(msgErro);
		this.status = status;
		
	}

}
