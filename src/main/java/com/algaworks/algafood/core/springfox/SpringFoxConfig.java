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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.algaworks.algafood.api.controller.openapi.model.CozinhasDtoOpenApi;
import com.algaworks.algafood.api.controller.openapi.model.PageableDtoOpenApi;
import com.algaworks.algafood.api.controller.openapi.model.PedidosResumoDtoOpenApi;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.model.CozinhaDto;
import com.algaworks.algafood.api.model.PedidoResumoDto;
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
	public Docket produceApi() {
    	TypeResolver typeResolver = new TypeResolver();
    	
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.algaworks.algafood.api"))
				.paths(PathSelectors.any())
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
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(Page.class, CozinhaDto.class),
					CozinhasDtoOpenApi.class
				)
			)
			.alternateTypeRules(AlternateTypeRules.newRule(
					typeResolver.resolve(Page.class, PedidoResumoDto.class),
					PedidosResumoDtoOpenApi.class
				)
			)
			.ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class, 
					Resource.class, File.class, InputStream.class)
			.apiInfo(apiInfo())
			.tags(new Tag("Cidades", "Gerencia as cidades"),
	    			 apiTags().toArray(new Tag[0]));
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
    		    new Tag("Estatísticas", "Estatísticas do JardaFood")
    		);
    }
    
    private ApiInfo apiInfo() {
    	return new ApiInfoBuilder()
    			.title("JardaFood - API")
    			.description("Api aberta para clientes e restaurantes")
    			.version("1")
    			.contact(new Contact("Jarda", "https://jardafood.com.br", "jardel.leandro25@gmail.com"))
    			.build();
    }
    
  
}