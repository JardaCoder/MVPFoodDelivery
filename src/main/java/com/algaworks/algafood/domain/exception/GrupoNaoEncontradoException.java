package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	
	private static final String MSG_GRUPO_NAO_EXISTE = "O grupo de código %d não existe.";

	public GrupoNaoEncontradoException(String mensagem) {
		super(mensagem);

	}

	public GrupoNaoEncontradoException(Long estadoId) {
		this(String.format(MSG_GRUPO_NAO_EXISTE, estadoId));

	}

}
