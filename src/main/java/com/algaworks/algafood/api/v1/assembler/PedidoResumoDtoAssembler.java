package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoResumoDto;
import com.algaworks.algafood.core.security.SecurityUtils;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDto>{
	
	public PedidoResumoDtoAssembler() {
		super(PedidoController.class, PedidoResumoDto.class);
	}
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private JardaLinks jardaLinks;
	@Autowired
	private SecurityUtils securityUtils;
	
    @Override
	public PedidoResumoDto toModel(Pedido pedido) {
		PedidoResumoDto pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDto);
        
        if(securityUtils.podePesquisarPedidos()) {
        	pedidoDto.add(jardaLinks.linkToPedidos());
        }
        
        if(securityUtils.podeConsultarRestaurantes()) {
        	pedidoDto.getRestaurante().add(
        			jardaLinks.linkToRestaurante(pedidoDto.getRestaurante().getId()));
        }
        
        if(securityUtils.podeConsultarUsuariosGruposPermissoes()) {
        	pedidoDto.getCliente().add(
        			jardaLinks.linkToUsuario(pedidoDto.getCliente().getId()));
        }

        return pedidoDto;
	}
    
    
    @Override
    public CollectionModel<PedidoResumoDto> toCollectionModel(Iterable<? extends Pedido> entities) {
    	return super.toCollectionModel(entities)
    			.add(jardaLinks.linkToPedidos());
    }	

}
