package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.model.UsuarioDto;
import com.algaworks.algafood.core.security.SecurityUtils;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDtoAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDto>   {
	
	public UsuarioDtoAssembler() {
		super(UsuarioController.class, UsuarioDto.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private JardaLinks jardaLinks;
	@Autowired
	private SecurityUtils securityUtils;
	
	@Override
	public UsuarioDto toModel(Usuario usuario) {
		
	
		UsuarioDto ususarioDto = createModelWithId(usuario.getId(), usuario);
		
		modelMapper.map(usuario, ususarioDto);
		
		if(securityUtils.podeConsultarUsuariosGruposPermissoes()) {
			ususarioDto.add(
					jardaLinks.linkToUsuarios("usuarios"));
			
			ususarioDto.add(
					jardaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
		}
		
		return ususarioDto;
	}
	
	
	@Override
	public CollectionModel<UsuarioDto> toCollectionModel(Iterable<? extends Usuario> entities) {
		return super.toCollectionModel(entities)
				.add(jardaLinks.linkToUsuarios());
	}

}
