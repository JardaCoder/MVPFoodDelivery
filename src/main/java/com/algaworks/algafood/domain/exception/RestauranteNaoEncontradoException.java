package com.algaworks.algafood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_RESTAURANTE_NAO_EXISTE = "O restaurante de código %d não existe.";

	public RestauranteNaoEncontradoException(String mensagem) {
		super(mensagem);

	}

	public RestauranteNaoEncontradoException(Long restauranteId) {
		this(String.format(MSG_RESTAURANTE_NAO_EXISTE, restauranteId));

	}

}
