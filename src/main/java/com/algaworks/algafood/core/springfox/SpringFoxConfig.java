package com.algaworks.algafood.core.springfox;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.controller.openapi.model.CidadesDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.CozinhasDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.EstadosDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.FormasPagamentoDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.GruposDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.LinksDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.PageableDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.PedidoResumoDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.PedidosResumoDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.PermissoesDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.ProdutosDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.RestaurantesBasicoDtoOpenApi;
import com.algaworks.algafood.api.v1.controller.openapi.model.UsuariosDtoOpenApi;
import com.algaworks.algafood.api.v1.model.CidadeDto;
import com.algaworks.algafood.api.v1.model.CozinhaDto;
import com.algaworks.algafood.api.v1.model.EstadoDto;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDto;
import com.algaworks.algafood.api.v1.model.GrupoDto;
import com.algaworks.algafood.api.v1.model.PedidoResumoDto;
import com.algaworks.algafood.api.v1.model.PermissaoDto;
import com.algaworks.algafood.api.v1.model.ProdutoDto;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoDto;
import com.algaworks.algafood.api.v1.model.UsuarioDto;
import com.algaworks.algafood.api.v2.controller.openapi.model.CidadesDtoOpenApiV2;
import com.algaworks.algafood.api.v2.controller.openapi.model.CozinhasDtoOpenApiV2;
import com.algaworks.algafood.api.v2.model.CidadeDtoV2;
import com.algaworks.algafood.api.v2.model.CozinhaDtoV2;
import com.fasterxml.classmate.TypeResolver;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{

    @Bean
	public Docket apiDocketV1() {
    	TypeResolver typeResolver = new TypeResolver();
    	
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V1")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
				.paths(PathSelectors.ant("/v1/**"))
				.build()
			.useDefaultResponseMessages(false)
			.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
			.globalResponseMessage(RequestMethod.POST, globalPostAndPutResponseMessages())
			.globalResponseMessage(RequestMethod.PUT, globalPostAndPutResponseMessages())
			.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
//			.globalOperationParameters(Arrays.asList(
//						new ParameterBuilder()
//							.name("campos")
//							.description("Nome das propriedades para filtrar na resposta, separados por virgula")
//							.parameterType("query")
//							.modelRef(new ModelRef("string"))
//							.build()
//					))
			.additionalModels(typeResolver.resolve(Problem.class))
			.directModelSubstitute(Pageable.class, PageableDtoOpenApi.class)
			.directModelSubstitute(Links.class, LinksDtoOpenApi.class)
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(PagedModel.class, CozinhaDto.class),
					CozinhasDtoOpenApi.class
				)
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(CollectionModel.class, CidadeDto.class),
					CidadesDtoOpenApi.class
				)
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(Page.class, PedidoResumoDto.class),
					PedidosResumoDtoOpenApi.class
				)
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
			        typeResolver.resolve(CollectionModel.class, EstadoDto.class),
			        EstadosDtoOpenApi.class
			    )
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
			        typeResolver.resolve(CollectionModel.class, FormaPagamentoDto.class),
			        FormasPagamentoDtoOpenApi.class
			    )
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
			        typeResolver.resolve(CollectionModel.class, GrupoDto.class),
			        GruposDtoOpenApi.class
			    )
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
			        typeResolver.resolve(CollectionModel.class, PermissaoDto.class),
			        PermissoesDtoOpenApi.class
			    )
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
			        typeResolver.resolve(CollectionModel.class, PedidoResumoDto.class),
			        PedidoResumoDtoOpenApi.class
			    )
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
			        typeResolver.resolve(CollectionModel.class, ProdutoDto.class),
			        ProdutosDtoOpenApi.class
			    )
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
			        typeResolver.resolve(CollectionModel.class, RestauranteBasicoDto.class),
			        RestaurantesBasicoDtoOpenApi.class
			    )
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
			        typeResolver.resolve(CollectionModel.class, UsuarioDto.class),
			        UsuariosDtoOpenApi.class
			    )
			)
			.ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, 
					Resource.class, File.class, InputStream.class)
			.apiInfo(apiInfoV1())
			.tags(new Tag("Cidades", "Gerencia as cidades"),
	    			 apiTags().toArray(new Tag[0]));
	}
    
    @Bean
	public Docket apiDocketV2() {
    	TypeResolver typeResolver = new TypeResolver();
    	
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("V2")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
				.paths(PathSelectors.ant("/v2/**"))
				.build()
			.useDefaultResponseMessages(false)
			.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
			.globalResponseMessage(RequestMethod.POST, globalPostAndPutResponseMessages())
			.globalResponseMessage(RequestMethod.PUT, globalPostAndPutResponseMessages())
			.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
			.additionalModels(typeResolver.resolve(Problem.class))
			.directModelSubstitute(Pageable.class, PageableDtoOpenApi.class)
			.directModelSubstitute(Links.class, LinksDtoOpenApi.class)
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(PagedModel.class, CozinhaDtoV2.class),
					CozinhasDtoOpenApiV2.class
				)
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(CollectionModel.class, CidadeDtoV2.class),
					CidadesDtoOpenApiV2.class
				)
			)
			.ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, 
					Resource.class, File.class, InputStream.class)
			.apiInfo(apiInfoV2())
			.tags(new Tag("Cidades", "Gerencia as cidades"),
				  new Tag("Cozinhas", "Gerencia as cozinhas")
			 );
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("swagger-ui.html")
    	.addResourceLocations("classpath:/META-INF/resources/");
    	
    	registry.addResourceHandler("/webjars/**")
    	.addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    
    private List<ResponseMessage> globalGetResponseMessages(){
    	
    	return Arrays.asList(
    			
    			new ResponseMessageBuilder()
	    			.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	    			.message("Erro interno do servidor")
	    			.responseModel(new ModelRef("Problema"))
	    			.build(),
    			
    			new ResponseMessageBuilder()
	    			.code(HttpStatus.NOT_ACCEPTABLE.value())
	    			.message("Recurso não possui representação que pode ser aceita pelo consumidor")
	    			.build()
    		);
    }
    
   private List<ResponseMessage> globalPostAndPutResponseMessages(){
    	
    	return Arrays.asList(
    			
    			new ResponseMessageBuilder()
	    			.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	    			.message("Erro interno do servidor")
	    			.responseModel(new ModelRef("Problema"))
	    			.build(),
    			
    			new ResponseMessageBuilder()
	    			.code(HttpStatus.NOT_ACCEPTABLE.value())
	    			.message("Recurso não possui representação que pode ser aceita pelo consumidor")
	    			.build(),
	    			
	    		new ResponseMessageBuilder()
	    			.code(HttpStatus.BAD_REQUEST.value())
	    			.message("Os dados da requisição são inválidos")
	    			.responseModel(new ModelRef("Problema"))
	    			.build(),
	    			
		    	new ResponseMessageBuilder()
	    			.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
	    			.message("Corpo não suportado.")
	    			.responseModel(new ModelRef("Problema"))
	    			.build()
    		);
    }
   
   
   private List<ResponseMessage> globalDeleteResponseMessages() {
	   	return Arrays.asList(
				
				new ResponseMessageBuilder()
	    			.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
	    			.message("Erro interno do servidor")
	    			.responseModel(new ModelRef("Problema"))
	    			.build(),
				
	    		new ResponseMessageBuilder()
	    			.code(HttpStatus.BAD_REQUEST.value())
	    			.message("Os dados da requisição são inválidos")
	    			.responseModel(new ModelRef("Problema"))
	    			.build()
			);
	}

   
    
    private List<Tag> apiTags(){
    	return Arrays.asList(
    			new Tag("Restaurantes", "Gerencia os restaurantes"),
    			new Tag("Grupos", "Gerencia os grupos"),
    			new Tag("Cozinhas", "Gerencia as cozinhas"),
    			new Tag("FormasPagamento", "Gerencia as formas de pagamento"),
    			new Tag("Estados", "Gerencia os estados"),
    		    new Tag("Produtos", "Gerencia os produtos de restaurantes"),
    		    new Tag("Usuários", "Gerencia os usuários"),
    		    new Tag("Estatísticas", "Estatísticas do JardaFood"),
    		    new Tag("Permissões", "Gerencia as permissões"),
    		    new Tag("RootEntryPoint", "Ponto inicial para seguir o HATEOAS")
    		);
    }
    
    private ApiInfo apiInfoV1() {
    	return new ApiInfoBuilder()
    			.title("JardaFood - API (Depreciada)")
    			.description("Api aberta para clientes e restaurantes. <br>"
    					+ "<strong> Essa versão da Api está depreciada e deixara de existir a partir de 01/01/2023"
    					+ "Use a versão mais recente. </strong>")
    			.version("1")
    			.contact(new Contact("Jarda", "https://jardafood.com.br", "jardel.leandro25@gmail.com"))
    			.build();
    }
    
    private ApiInfo apiInfoV2() {
    	return new ApiInfoBuilder()
    			.title("JardaFood - API")
    			.description("Api aberta para clientes e restaurantes")
    			.version("2")
    			.contact(new Contact("Jarda", "https://jardafood.com.br", "jardel.leandro25@gmail.com"))
    			.build();
    }
    
  
}