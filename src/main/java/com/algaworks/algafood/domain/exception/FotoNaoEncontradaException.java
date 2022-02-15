package com.algaworks.algafood.domain.exception;

public class FotoNaoEncontradaException extends EntidadeNaoEncontradaException{


	private static final long serialVersionUID = 1L;
	
	private static final String MSG_PRODUTO_NAO_ENCONTRADO = "A foto para o produto de código %d não foi encontrada no restaurante de código %d";

	public FotoNaoEncontradaException(String mensagem) {
		super(mensagem);

	}

	public FotoNaoEncontradaException(Long produtoId, Long restauranteId) {
		this(String.format(MSG_PRODUTO_NAO_ENCONTRADO, produtoId, restauranteId));

	}
}
