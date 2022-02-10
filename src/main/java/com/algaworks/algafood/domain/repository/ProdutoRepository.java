package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import com.algaworks.algafood.domain.model.Produto;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>{

	Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);

}
