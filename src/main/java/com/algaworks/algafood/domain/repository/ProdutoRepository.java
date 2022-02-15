package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

public interface ProdutoRepository extends CustomJpaRepository<Produto, Long>, ProdutoRepositoryQueries{

	Optional<Produto> findByIdAndRestauranteId(Long produtoId, Long restauranteId);

	@Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
	List<Produto> findAtivosByRestaurante(Restaurante restaurante);
	
	
	@Query("SELECT f FROM FotoProduto f JOIN f.produto p  WHERE f.produto.id = :produtoId AND p.restaurante.id = :restauranteId")
	Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);



}
