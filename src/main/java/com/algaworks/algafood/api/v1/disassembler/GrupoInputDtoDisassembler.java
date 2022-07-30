package com.algaworks.algafood.api.v1.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.GrupoInputDto;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoInputDtoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Grupo grupoInputDtoToGrupo(GrupoInputDto grupoInputDto) {
		return modelMapper.map(grupoInputDto, Grupo.class);
	}
	
	public void copyToDomainObject(GrupoInputDto grupoInputDto, Grupo grupo) {
		modelMapper.map(grupoInputDto, grupo);
	}
}
