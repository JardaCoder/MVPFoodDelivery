package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CidadeInputDto;
import com.algaworks.algafood.domain.model.Cidade;

@Component
public class CidadeInputDtoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Cidade cidadeInputDtoToCidade(CidadeInputDto cidadeInputDto) {
		
		return modelMapper.map(cidadeInputDto, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDto cidadeInput, Cidade cidade) {
		modelMapper.map(cidadeInput, cidade);
	}
}
