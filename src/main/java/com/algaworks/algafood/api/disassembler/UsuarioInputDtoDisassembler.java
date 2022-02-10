package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.UsuarioInputDto;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDtoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Usuario usuarioInputDtoToUsuario(UsuarioInputDto usuarioInputDto) {
		return modelMapper.map(usuarioInputDto, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInputDto usuarioInputDto, Usuario usuario) {
		modelMapper.map(usuarioInputDto, usuario);
	}
} 
