package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_ESTADO_NAO_EXISTE = "O estado de código %d não existe.";

	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);

	}

	public EstadoNaoEncontradoException(Long estadoId) {
		this(String.format(MSG_ESTADO_NAO_EXISTE, estadoId));

	}

}
