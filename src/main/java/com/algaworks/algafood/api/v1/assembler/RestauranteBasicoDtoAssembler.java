package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoDto;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteBasicoDtoAssembler 
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDto>{
	
	
    public RestauranteBasicoDtoAssembler() {
        super(RestauranteController.class, RestauranteBasicoDto.class);
    }
    
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JardaLinks jardaLinks;
	
	@Override
	public RestauranteBasicoDto toModel(Restaurante restaurante) {
		RestauranteBasicoDto restauranteDto = createModelWithId(
                restaurante.getId(), restaurante);
		
		 modelMapper.map(restaurante, restauranteDto);

		 restauranteDto.add(jardaLinks.linkToRestaurantes("restaurantes"));
        
		 restauranteDto.getCozinha().add(
        		jardaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		
		 return restauranteDto;
	}
	
	@Override
	public CollectionModel<RestauranteBasicoDto> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(jardaLinks.linkToRestaurantes());
	}
	

}
