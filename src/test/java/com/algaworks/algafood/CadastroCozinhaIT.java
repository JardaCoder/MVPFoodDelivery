package com.algaworks.algafood;





import static com.algaworks.algafood.util.ResourceFileUtils.getJsonFromResourceFile;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.enums.RequestType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {
	
	
	
	private static final int COZINHA_ID_INEXISTENTE = 100;
	
	Long quantidadeCozinha = 0L;
	Cozinha cozinhaBrasileira;
	
	String cadastroCozinhaCorreto;

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
		
	@Before
	public void setUp() throws JsonParseException, JsonMappingException, IOException {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();	
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	
		databaseCleaner.clearTables();
		prepararDados();
		
		this.cadastroCozinhaCorreto = getJsonFromResourceFile("CadastroCozinha.json", RequestType.SUCCESS);
	}
	
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveChecarNumeroDeCozinhas_QuandoConsultarCozinhas() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(this.quantidadeCozinha.intValue()));
		
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinha() throws JsonParseException, JsonMappingException, IOException { 
		
		given()
			.body(this.cadastroCozinhaCorreto)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarStatus200EResposta_QuandoConsultarCozinhas() {
		
		given()
			.pathParam("cozinhaId", this.cozinhaBrasileira.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(this.cozinhaBrasileira.getNome()));
	}
	
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		
		given()
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	
	private void prepararDados() {
		
		var cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		
		var cozinha2 = new Cozinha();
		cozinha2.setNome("Brasileira");
		
		cozinhaRepository.saveAll(Arrays.asList(cozinha1, cozinha2));
		
		this.cozinhaBrasileira = cozinha2;
		
		this.quantidadeCozinha = cozinhaRepository.count();
	}
	
}
