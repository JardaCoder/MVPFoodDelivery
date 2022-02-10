package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {

	private static final String MSG_RESTAURANTE_NAO_ACEITA_FORMA_PAGAMENTO = "O restaurante %s não aceita forma de pagamento %s";

	private static final String MSG_PEDIDO_SEM_ITEM = "Pedido deve conter pelo menos um item.";

	
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private CadastroCidadeService cadastroCidade;
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	

	@Transactional
	public Pedido salvar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	
	public Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido)
				.orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}
		
	@Transactional
	public Pedido processarPedido(Pedido pedido) {
		
		verificarSeInformacoesValidas(pedido);
		validarProdutosDoPedido(pedido);
		
		pedido.calcularValorTotal();
		
		return salvar(pedido);
		
	}
	
	
	public void verificarSeInformacoesValidas(Pedido pedido) {
		
			Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
			Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(pedido.getRestaurante().getId());
			FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(pedido.getFormaPagamento().getId());
			Usuario cliente = cadastroUsuarioService.buscarOuFalhar(pedido.getCliente().getId());
			
			if(!restaurante.aceitaFormaPagamento(formaPagamento)) {
				throw new NegocioException(String
						.format(MSG_RESTAURANTE_NAO_ACEITA_FORMA_PAGAMENTO, restaurante.getNome(), formaPagamento.getDescricao()));
			}
			
			pedido.getEnderecoEntrega().setCidade(cidade);
			pedido.setRestaurante(restaurante);
			pedido.setTaxaFrete(restaurante.getTaxaFrete());
			pedido.setFormaPagamento(formaPagamento);
			pedido.setCliente(cliente);
	}
	
	public void validarProdutosDoPedido(Pedido pedido) {
		
		if(pedido.getItens().isEmpty()) {
			throw new NegocioException(MSG_PEDIDO_SEM_ITEM);
		}
		
		pedido.getItens().forEach(item ->{
				Produto produto = cadastroProdutoService
						.buscarOuFalhar(item.getProduto().getId(), pedido.getRestaurante().getId());
				
				item.setProduto(produto);
				item.setPedido(pedido);
				item.setPrecoUnitario(produto.getPreco());
		});
		
	}
}
