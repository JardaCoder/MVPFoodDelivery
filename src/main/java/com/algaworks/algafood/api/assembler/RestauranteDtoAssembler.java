package com.algaworks.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.RestauranteDto;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDtoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RestauranteDto restauranteToRestauranteDto(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDto.class);
	}
	
	public List<RestauranteDto> restaurantesToListRestauranteDto(List<Restaurante> restaurantes) {
		return restaurantes.stream().map(this::restauranteToRestauranteDto).collect(Collectors.toList());
	}
	

}
