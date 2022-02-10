package com.algaworks.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.UsuarioDto;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioDtoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDto.class);
	}
	
	public List<UsuarioDto> usuariosToListUsuarioDto(Collection<Usuario> usuarios) {
		return usuarios.stream().map(this::usuarioToUsuarioDto).collect(Collectors.toList());
	}
	

}
