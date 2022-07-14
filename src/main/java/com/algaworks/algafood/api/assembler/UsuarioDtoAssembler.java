package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.model.UsuarioDto;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDtoAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDto>   {
	
	public UsuarioDtoAssembler() {
		super(UsuarioController.class, UsuarioDto.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UsuarioDto toModel(Usuario usuario) {
		
	
		UsuarioDto ususarioDto = createModelWithId(usuario.getId(), usuario);
		
		modelMapper.map(usuario, ususarioDto);
		
		ususarioDto.add(linkTo(methodOn(UsuarioController.class)
				.listar())
				.withRel("usuarios"));
		
		ususarioDto.add(linkTo(methodOn(UsuarioGrupoController.class)
				.listar(ususarioDto.getId()))
				.withRel("grupos-usuario"));
		
		return ususarioDto;
	}
	
	
	@Override
	public CollectionModel<UsuarioDto> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(UsuarioController.class).withSelfRel());
	}

}
