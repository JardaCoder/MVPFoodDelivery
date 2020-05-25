package com.algaworks.algafood.domain.exception;

public class JardaFoodException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	
	public JardaFoodException (String mensagem) {
		super(mensagem);
	}
}
