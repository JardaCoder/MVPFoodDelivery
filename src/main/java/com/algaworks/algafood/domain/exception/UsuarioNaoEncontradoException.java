package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	
	private static final String MSG_USUARIO_NAO_EXISTE = "O usuário de código %d não existe.";

	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);

	}

	public UsuarioNaoEncontradoException(Long estadoId) {
		this(String.format(MSG_USUARIO_NAO_EXISTE, estadoId));

	}

}
