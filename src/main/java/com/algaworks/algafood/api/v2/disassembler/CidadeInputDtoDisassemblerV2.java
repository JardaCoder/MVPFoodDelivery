package com.algaworks.algafood.api.v2.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.model.input.CidadeInputDtoV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDtoDisassemblerV2 {
	
	@Autowired
	private ModelMapper modelMapper;

	public Cidade cidadeInputDtoToCidade(CidadeInputDtoV2 cidadeInputDto) {
		
		return modelMapper.map(cidadeInputDto, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDtoV2 cidadeInput, Cidade cidade) {
		
		//Contornar erro do JPA que acha que estamos modificando o ID do estado.
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInput, cidade);
	}
}
