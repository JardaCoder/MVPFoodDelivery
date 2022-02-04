package com.algaworks.algafood;





import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCozinhaIT_OLD {

	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	// De se seguir sempre o fluxo:  Cenário, Ação, Validação.
	
	@Test
	public void sucesso_CestarCadastroCozinha() {
		//cenário
		var novaCozinha =  new Cozinha();
		novaCozinha.setNome("Chinesa");
		
		//ação
		cadastroCozinha.salvar(novaCozinha);
		
		//validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void falhar_CadastroCozinhaSemNome() {
		//cenário
		var novaCozinha =  new Cozinha();
		novaCozinha.setNome(null);
		
		//ação
		cadastroCozinha.salvar(novaCozinha);
		
	}
	
	@Test(expected = EntidadeEmUsoException.class)
	public void falhar_ExcluirCozinhaEmUso() {
		cadastroCozinha.excluir(1L);
	}
	
	@Test(expected = CozinhaNaoEncontradaException.class)
	public void falhar_ExcluirCozinhaInexistente() {
		cadastroCozinha.excluir(100L);
	}
}
