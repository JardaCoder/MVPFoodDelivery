package com.algaworks.algafood.api.v2.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v2.model.input.CozinhaInputDtoV2;
import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputDtoDisassemblerV2 {
	
	@Autowired
	private ModelMapper modelMapper;

	public Cozinha cozinhaInputDtoToCidade(CozinhaInputDtoV2 cozinhaInputDto) {
		return modelMapper.map(cozinhaInputDto, Cozinha.class);
	}
	
	public void copyToDomainObject(CozinhaInputDtoV2 cozinhaInputDto, Cozinha cozinha) {
		modelMapper.map(cozinhaInputDto, cozinha);
	}
}
