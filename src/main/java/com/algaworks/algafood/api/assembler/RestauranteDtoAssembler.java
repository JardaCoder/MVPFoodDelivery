package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.JardaLinks;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.model.RestauranteDto;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteDtoAssembler 
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDto>{
	
	
    public RestauranteDtoAssembler() {
        super(RestauranteController.class, RestauranteDto.class);
    }
    
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JardaLinks jardaLinks;
	
	
	@Override
	public RestauranteDto toModel(Restaurante restaurante) {
		RestauranteDto restauranteDto = createModelWithId(
                restaurante.getId(), restaurante);
		
		 modelMapper.map(restaurante, restauranteDto);

		 restauranteDto.add(jardaLinks.linkToRestaurantes("restaurantes"));
 
		if (restaurante.ativacaoPermitida()) {
			restauranteDto.add(
					jardaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
		}
		
		if (restaurante.inativacaoPermitida()) {
			restauranteDto.add(
					jardaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
		}
		
		if (restaurante.aberturaPermitida()) {
			restauranteDto.add(
					jardaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
		}
		
		if (restaurante.fechamentoPermitido()) {
			restauranteDto.add(
					jardaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
		}
        
		restauranteDto.add(jardaLinks.linkToProdutos(restaurante.getId(), "produtos"));
		
	    if (restauranteDto.getEndereco() != null 
	            && restauranteDto.getEndereco().getCidade() != null) {
	    	restauranteDto.getEndereco().getCidade().add(
	    			jardaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
	    }

		restauranteDto.getCozinha().add(
        		jardaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		 
		 restauranteDto.getEndereco().getCidade().add(
				 jardaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
	        
		 restauranteDto.add(jardaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), 
	                "formas-pagamento"));
	        
		 restauranteDto.add(jardaLinks.linkToResponsaveisRestaurante(restaurante.getId(), 
	                "responsaveis"));
		
		 return restauranteDto;
	}
	
	@Override
	public CollectionModel<RestauranteDto> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities)
				.add(jardaLinks.linkToRestaurantes());
	}
}
