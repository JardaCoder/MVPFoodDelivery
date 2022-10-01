package com.algaworks.algafood.core.security.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {
	
	@PreAuthorize("isAuthenticated()")
	@Retention(RUNTIME)
	@Target(METHOD)
	public @interface DeveEstarAutenticado {}
	
	public @interface Cozinhas{
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {}
		
		
		@PreAuthorize("@securityUtils.podeConsultarCozinhas()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}

	}
	
	public @interface Restaurantes {

	    @PreAuthorize("@securityUtils.podeGerenciarCadastroRestaurantes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeGerenciarCadastro { }
	    
	    @PreAuthorize("@securityUtils.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeGerenciarFuncionamento { }

	    @PreAuthorize("@securityUtils.podeConsultarRestaurantes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Pedidos {
		
		@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
		@PostAuthorize("(hasAuthority('CONSULTAR_PEDIDOS') or "
				+ "@securityUtils.usuarioAutenticadoIgual(returnObject.cliente.id) or "
				+ "@securityUtils.gerenciaRestaurante(returnObject.restaurante.id))")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeBuscar { }
		
		@PreAuthorize("@securityUtils.podePesquisarPedidos(#pedidoFilter.clienteId, #pedidoFilter.restauranteId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodePesquisar { }
		
		@PreAuthorize("@securityUtils.podeCriarPedidos()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeCriar { }

		@PreAuthorize("@securityUtils.podeGerenciarPedidos(#codigoPedido)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarPedidos {
		}
	}
	
	public @interface FormasPagamento {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

	    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Cidades {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

	    @PreAuthorize("@securityUtils.podeConsultarCidades()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}

	public @interface Estados {
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

	    @PreAuthorize("@securityUtils.podeConsultarEstados()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface UsuariosGruposPermissoes {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
	            + "@securityUtils.usuarioAutenticadoIgual(#usuarioId)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeAlterarPropriaSenha { }
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
	            + "@securityUtils.usuarioAutenticadoIgual(#usuarioId))")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeAlterarUsuario { }

	    @PreAuthorize("@securityUtils.podeEditarUsuariosGruposPermissoes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }
	    
	    @PreAuthorize("@securityUtils.podeConsultarUsuariosGruposPermissoes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Estatisticas {

		@PreAuthorize("@securityUtils.podeConsultarEstatisticas()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}

}
