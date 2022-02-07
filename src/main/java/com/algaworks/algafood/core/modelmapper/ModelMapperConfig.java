package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
	
//      como referenciar getters e setters não automaticamente mapeados
//		modelMapper.createTypeMap(Restaurante.class, RestauranteDto.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteDto::setPrecoFrete);
		
		return modelMapper;
	}

}
