package com.algaworks.algafood.api.v1.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.model.input.CidadeInputDto;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDtoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Cidade cidadeInputDtoToCidade(CidadeInputDto cidadeInputDto) {
		
		return modelMapper.map(cidadeInputDto, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDto cidadeInput, Cidade cidade) {
		
		//Contornar erro do JPA que acha que estamos modificando o ID do estado.
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInput, cidade);
	}
}
