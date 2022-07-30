package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.assembler.UsuarioDtoAssembler;
import com.algaworks.algafood.api.v1.controller.openapi.controller.RestauranteUsuarioControllerOpenApi;
import com.algaworks.algafood.api.v1.model.UsuarioDto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioControlller implements RestauranteUsuarioControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	@Autowired
	private UsuarioDtoAssembler usuarioDtoAssembler;
	@Autowired
	private JardaLinks jardaLinks;

	@Override
	@GetMapping
	public CollectionModel<UsuarioDto> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		
		CollectionModel<UsuarioDto> usuarios =  usuarioDtoAssembler
				.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks()
				.add(jardaLinks.linkToResponsaveisRestaurante(restauranteId))
				.add(jardaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));
		
		usuarios.getContent().forEach(usuario ->{
			usuario.add(jardaLinks
					.linkToRestauranteResponsavelDesassociacao(
							restauranteId, usuario.getId(), "desassociar"));
		});
		
		return usuarios;
	}

	@Override
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.desassociarUsuario(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/{usuarioId}")
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.associarUsuario(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}

}
