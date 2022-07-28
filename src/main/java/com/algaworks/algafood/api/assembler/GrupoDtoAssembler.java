package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.JardaLinks;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.model.GrupoDto;
import com.algaworks.algafood.domain.model.Grupo;

@Component
public class GrupoDtoAssembler 
extends RepresentationModelAssemblerSupport<Grupo, GrupoDto>  {
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private JardaLinks jardaLinks;
	
    public GrupoDtoAssembler() {
        super(GrupoController.class, GrupoDto.class);
    }
	
    @Override
	public GrupoDto toModel(Grupo grupo) {
		GrupoDto grupoModel = createModelWithId(grupo.getId(), grupo);
	    modelMapper.map(grupo, grupoModel);
	    
	    grupoModel.add(jardaLinks.linkToGrupos("grupos"));
	    
	    grupoModel.add(jardaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
	    
	    return grupoModel;
	}
	
    
    @Override
    public CollectionModel<GrupoDto> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(jardaLinks.linkToGrupos());
    }  

}
