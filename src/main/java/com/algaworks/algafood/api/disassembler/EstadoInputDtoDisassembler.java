package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.EstadoInputDto;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoInputDtoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Estado estadoInputDtoToEstado(EstadoInputDto estadoInputDto) {
		return modelMapper.map(estadoInputDto, Estado.class);
	}
	
	public void copyToDomainObject(EstadoInputDto estadoInputDto, Estado estado) {
		modelMapper.map(estadoInputDto, estado);
	}
}
