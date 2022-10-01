package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.JardaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.model.PedidoDto;
import com.algaworks.algafood.core.security.SecurityUtils;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDto>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JardaLinks jardaLinks;
	
	@Autowired
	private SecurityUtils securityUtils;
	
	 public PedidoDtoAssembler() {
        super(PedidoController.class, PedidoDto.class);
	 }
	
	public PedidoDto toModel(Pedido pedido) {
		PedidoDto pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDto);
        
      
        pedidoDto.add(jardaLinks.linkToPedidos("pedidos"));
        
        if(securityUtils.podeGerenciarPedidos(pedido.getCodigo())) {
        	if(pedido.podeSerConfirmado()) {
        		pedidoDto.add(
        				jardaLinks.linkToConfirmacaoPedido(pedidoDto.getCodigo(), "confirmar"));
        	}
        	
        	if(pedido.podeSerEntregue()) {
        		pedidoDto.add(
        				jardaLinks.linkToEntregaPedido(pedidoDto.getCodigo(), "entregar"));
        	}
        	
        	if(pedido.podeSerCancelado()) {
        		pedidoDto.add(
        				jardaLinks.linkToCancelarPedido(pedidoDto.getCodigo(), "cancelar"));
        	}        	
        }
        
        if(securityUtils.podeConsultarRestaurantes()) {
        	pedidoDto.getRestaurante().add(
        			jardaLinks.linkToRestaurante(pedidoDto.getRestaurante().getId()));
        }
        
        if(securityUtils.podeConsultarUsuariosGruposPermissoes()) {
        	pedidoDto.getCliente().add(
        			jardaLinks.linkToUsuario(pedidoDto.getCliente().getId()));
        }
        
        if(securityUtils.podeConsultarFormasPagamento()) {
        	pedidoDto.getFormaPagamento().add(
        			jardaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        }
        
        if(securityUtils.podeConsultarCidades()) {
        	pedidoDto.getEnderecoEntrega().getCidade().add(
        			jardaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
        }
        
        if(securityUtils.podeConsultarRestaurantes()) {
        	pedidoDto.getItens().forEach(item -> {
        		item.add(jardaLinks.linkToProduto(item.getProdutoId(), pedidoDto.getRestaurante().getId(), "produto"));
        	});
        }
        
        return pedidoDto;
	}
	
	@Override
	public CollectionModel<PedidoDto> toCollectionModel(Iterable<? extends Pedido> entities) {
		return super.toCollectionModel(entities)
				.add(jardaLinks.linkToPedidos());
	}

}
