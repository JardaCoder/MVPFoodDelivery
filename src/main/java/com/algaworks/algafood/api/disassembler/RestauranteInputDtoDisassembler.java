package com.algaworks.algafood.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.RestauranteInputDto;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDtoDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante restauranteInputDtoToRestaurante(RestauranteInputDto restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInputDto restauranteInput, Restaurante restaurante) {
		modelMapper.map(restauranteInput, restaurante);
	}
}
