package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.JardaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.model.PedidoResumoDto;
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
	
	
    @Override
	public PedidoResumoDto toModel(Pedido pedido) {
		PedidoResumoDto pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDto);
        
        pedidoDto.add(jardaLinks.linkToPedidos());
        
        pedidoDto.getRestaurante().add(
        		jardaLinks.linkToRestaurante(pedidoDto.getRestaurante().getId()));
        
        pedidoDto.getCliente().add(
        		jardaLinks.linkToUsuario(pedidoDto.getCliente().getId()));
        
        return pedidoDto;
	}
    
    
    @Override
    public CollectionModel<PedidoResumoDto> toCollectionModel(Iterable<? extends Pedido> entities) {
    	return super.toCollectionModel(entities)
    			.add(jardaLinks.linkToPedidos());
    }	

}
