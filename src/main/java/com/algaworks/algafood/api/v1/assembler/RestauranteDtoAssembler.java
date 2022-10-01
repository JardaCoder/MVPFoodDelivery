package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteDto;
import com.algaworks.algafood.core.security.SecurityUtils;
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
	@Autowired
	private SecurityUtils securityUtils;
	
	
	@Override
	public RestauranteDto toModel(Restaurante restaurante) {
		RestauranteDto restauranteDto = createModelWithId(restaurante.getId(), restaurante);

		modelMapper.map(restaurante, restauranteDto);

		if (securityUtils.podeConsultarRestaurantes()) {
			restauranteDto.add(jardaLinks.linkToRestaurantes("restaurantes"));
		}

		if (securityUtils.podeGerenciarCadastroRestaurantes()) {
			if (restaurante.ativacaoPermitida()) {
				restauranteDto.add(jardaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
			}

			if (restaurante.inativacaoPermitida()) {
				restauranteDto.add(jardaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
			}
		}

		if (securityUtils.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
			if (restaurante.aberturaPermitida()) {
				restauranteDto.add(jardaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
			}

			if (restaurante.fechamentoPermitido()) {
				restauranteDto.add(jardaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
			}
		}

		if (securityUtils.podeConsultarCozinhas()) {
			restauranteDto.getCozinha().add(jardaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		}

		if (securityUtils.podeConsultarCidades()) {
			if (restauranteDto.getEndereco() != null && restauranteDto.getEndereco().getCidade() != null) {
				restauranteDto.getEndereco().getCidade()
						.add(jardaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
			}
		}

		if (securityUtils.podeConsultarRestaurantes()) {
			restauranteDto.add(jardaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));
		}

		if (securityUtils.podeGerenciarCadastroRestaurantes()) {
			restauranteDto.add(jardaLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));
		}

		if (securityUtils.podeConsultarRestaurantes()) {
			restauranteDto.add(jardaLinks.linkToProdutos(restaurante.getId(), "produtos"));
		}

		return restauranteDto;
	}
	
	@Override
	public CollectionModel<RestauranteDto> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteDto> collectionModel = super.toCollectionModel(entities);
		
		if (securityUtils.podeConsultarRestaurantes()) {
			 collectionModel.add(jardaLinks.linkToRestaurantes());
		}

		return collectionModel;
	}
}
