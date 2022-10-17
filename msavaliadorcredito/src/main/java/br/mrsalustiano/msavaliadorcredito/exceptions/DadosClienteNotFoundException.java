package br.mrsalustiano.msavaliadorcredito.exceptions;

public class DadosClienteNotFoundException extends Exception {

  
	public DadosClienteNotFoundException() {
		super("Dados do cliente Não localizado para o CPF informado.");
	}
	
	

}
