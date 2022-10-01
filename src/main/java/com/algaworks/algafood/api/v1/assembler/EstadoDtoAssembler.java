package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.model.EstadoDto;
import com.algaworks.algafood.core.security.SecurityUtils;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class EstadoDtoAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDto> {
	
	public EstadoDtoAssembler() {
		super(EstadoController.class, EstadoDto.class);
	}

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private JardaLinks jardaLinks;
	@Autowired
	private SecurityUtils securityUtils;
	
	public EstadoDto toModel(Estado estado) {
			
		EstadoDto estadoDto = createModelWithId(estado.getId(), estado);
		
		modelMapper.map(estado, estadoDto);
		
		if(securityUtils.podeConsultarEstados()) {
			estadoDto.add(jardaLinks.linkToEstados("estados"));
		}
		
		return estadoDto;
	}
	
	@Override
	public CollectionModel<EstadoDto> toCollectionModel(Iterable<? extends Estado> entities) {
	    CollectionModel<EstadoDto> collectionModel = super.toCollectionModel(entities);
	    
	    if (securityUtils.podeConsultarEstados()) {
	        collectionModel.add(jardaLinks.linkToEstados());
	    }
	    
	    return collectionModel;
	}
}
