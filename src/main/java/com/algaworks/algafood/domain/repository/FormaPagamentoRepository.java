package com.algaworks.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long> {


	@Query("SELECT max(dataAtualizacao) FROM FormaPagamento")
	OffsetDateTime getDataUltimaAtualizacao();
	
	@Query("SELECT max(dataAtualizacao) FROM FormaPagamento f WHERE f.id = :formaPagamentoId")
	OffsetDateTime getDataUltimaAtualizacaoById(Long formaPagamentoId);
}
