package com.algaworks.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.JardaLinks;
import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteControlller;
import com.algaworks.algafood.api.controller.RestauranteProdutoControlller;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.PedidoDto;
import com.algaworks.algafood.domain.model.Pedido;

@Component
public class PedidoDtoAssembler  extends RepresentationModelAssemblerSupport<Pedido, PedidoDto>{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JardaLinks jardaLinks;
	
	 public PedidoDtoAssembler() {
	        super(PedidoController.class, PedidoDto.class);
	    }
	
	public PedidoDto toModel(Pedido pedido) {
		PedidoDto pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDto);
        
      
        pedidoDto.add(jardaLinks.linkToPedidos());
        
        pedidoDto.getRestaurante().add(linkTo(methodOn(RestauranteControlller.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());
        
        pedidoDto.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());
        
        // Passamos null no segundo argumento, porque é indiferente para a
        // construção da URL do recurso de forma de pagamento
        pedidoDto.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                .buscar(pedido.getFormaPagamento().getId(), null)).withSelfRel());
        
        pedidoDto.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
                .buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());
        
        pedidoDto.getItens().forEach(item -> {
            item.add(linkTo(methodOn(RestauranteProdutoControlller.class)
                    .buscar(pedidoDto.getRestaurante().getId(), item.getProdutoId()))
                    .withRel("produto"));
        });
        
        return pedidoDto;
	}
	
	public List<PedidoDto> pedidosToListPedidoDto(List<Pedido> pedidos) {
		return pedidos.stream().map(this::toModel).collect(Collectors.toList());
	}

}
