package com.algaworks.algafood;





import static com.algaworks.algafood.util.ResourceFileUtils.getJsonFromResourceFile;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.enums.RequestType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {
	
	private static final int RESTAURANTE_ID_INEXISTENTE = 100;
	
	Long quantidadeRestaurante = 0L;
	Cozinha cozinhaBrasileira;
	
	private Restaurante restauranteJardaBurguer;
	
	
	//Bodys
	String cadastroCozinhaCorreta;
	
	String cadastroRestauranteCorreto;
	String cadastroRestauranteSemCozinha;
	String cadastroRestauranteCozinhaInexistente;
	
	

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;

	
		
	@Before
	public void setUp() throws JsonParseException, JsonMappingException, IOException {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();	
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		cadastroCozinhaCorreta = getJsonFromResourceFile("CadastroCozinha.json", RequestType.SUCCESS);
		cadastroRestauranteCorreto = getJsonFromResourceFile("cadastro-restaurante.json", RequestType.SUCCESS);
		
		
		//error
		cadastroRestauranteSemCozinha = getJsonFromResourceFile("cadastro-restaurante-sem-cozinha.json", RequestType.FAIL);
		cadastroRestauranteCozinhaInexistente = getJsonFromResourceFile("cadastro-restaurante-cozinha-inexistente.json", RequestType.FAIL);
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornarStatus200EResposta_QuandoConsultarRestaurante() {
		
		Restaurante restaurante = given()
			.pathParam("restauranteId", restauranteJardaBurguer.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.response()
			.as(Restaurante.class);
		
		assertEquals(restaurante.getTaxaFrete(), restauranteJardaBurguer.getTaxaFrete());
		assertEquals(restaurante.getNome(), restauranteJardaBurguer.getNome());
		assertEquals(restaurante.getCozinha().getId(), restauranteJardaBurguer.getCozinha().getId());
		
	}
	
	@Test
	public void deveChecarNumeroDeResutantes_QuandoConsultarRestaurantes() {
		
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", hasSize(quantidadeRestaurante.intValue()));
		
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante() throws JsonParseException, JsonMappingException, IOException {
		
		given()
			.body(cadastroRestauranteCorreto)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha() throws JsonParseException, JsonMappingException, IOException {
		
		given()
			.body(cadastroRestauranteSemCozinha)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente() throws JsonParseException, JsonMappingException, IOException {
		
		given()
			.body(cadastroRestauranteCozinhaInexistente)
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}	
	
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		
		given()
			.pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{restauranteId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	
	private void prepararDados() {
		
		var cozinhaTailandesa = new Cozinha();
		cozinhaTailandesa.setNome("Tailandesa");
		
		var cozinhaBrasileira= new Cozinha();
		cozinhaBrasileira.setNome("Brasileira");
		
		cozinhaRepository.saveAll(Arrays.asList(cozinhaTailandesa, cozinhaBrasileira));
		
		restauranteJardaBurguer = new Restaurante();
		
		restauranteJardaBurguer.setNome("Jarda burguer");
		restauranteJardaBurguer.setTaxaFrete(new BigDecimal("10.00"));
		restauranteJardaBurguer.setCozinha(cozinhaBrasileira);
		
		
		var restaurante2 = new Restaurante();
		
		restaurante2.setNome("Jarda Lanches");
		restaurante2.setTaxaFrete(new BigDecimal("5.00"));
		restaurante2.setCozinha(cozinhaTailandesa);
		
		restauranteRepository.saveAll(Arrays.asList(restauranteJardaBurguer, restaurante2));
		
		
		this.cozinhaBrasileira = cozinhaBrasileira;
		
		quantidadeRestaurante = restauranteRepository.count();
	}
	
	
}
