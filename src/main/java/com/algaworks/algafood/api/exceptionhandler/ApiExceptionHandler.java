package com.algaworks.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final String MSG_ERRO_GENERICA_UI = "Ocorreu um erro interno inesperado no sistema. Tente novamente "
			+ "e se o problema persistir, entre em contato com o administrador do sistema.";
	
	@Autowired
	private MessageSource messageSource;
	

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException e, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		String detail  = e.getMessage();
		ProblemType  problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		String detail  = e.getMessage();
		ProblemType  problemType = ProblemType.ERRO_NEGOCIO;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(e.getMessage())
				.build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity<?> handleValidacaoException(ValidacaoException e, WebRequest request){
		
		return handleInternalValidation(e, e.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request){
		
		HttpStatus status = HttpStatus.CONFLICT;
		String detail  = e.getMessage();
		ProblemType  problemType = ProblemType.ENTIDADE_EM_USO;
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGenericExecption(Exception e, WebRequest request){
	
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		String detail  = MSG_ERRO_GENERICA_UI;
		ProblemType  problemType = ProblemType.ERRO_DE_SISTEMA;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_UI)
				.build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		return handleInternalValidation(ex, ex.getBindingResult(), headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(body == null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.userMessage(MSG_ERRO_GENERICA_UI)
					.build();
		}else if (body instanceof String) {
			body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.userMessage(MSG_ERRO_GENERICA_UI)
					.build();
		}
	
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	

	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(e);
		
		if(rootCause instanceof InvalidFormatException) {
			
			return handleInvalidFormatExeception((InvalidFormatException) rootCause, headers, status, request);
			
		}else if(rootCause instanceof PropertyBindingException) {
			
			return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
		}
		
		String detail  = "O corpo da requisição está inválido. Verifique erro de sintaxe";
		ProblemType  problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_UI)
				.build();
		
		return handleExceptionInternal(e, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(e instanceof MethodArgumentTypeMismatchException) {
			
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) e, headers, status, request);
			
		}
		
		 return super.handleTypeMismatch(e, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
	
		String detail  = String.format("O recurso '%s', que você tentou acessar, é inexistente", e.getRequestURL());
		ProblemType  problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return handleInternalValidation(e, e.getBindingResult(), headers, status, request);

	}
	
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		String detail  = String.format("O parametro de URL '%s' recebeu o valor '%s' que é um tipo inválido."
				+ " Corrija e informe um valor compativel com o tipo %s", e.getName(), e.getValue(), e.getRequiredType().getSimpleName());
		

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;

		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(e, problem, headers, status, request);
	}
	
	
	
	private ResponseEntity<Object> handleInternalValidation(Exception e, BindingResult bindingResults,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String detail  = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		String userMessage = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		ProblemType  problemType = ProblemType.DADOS_INVALIDOS;
		

		List<Problem.Object> problemObjects = bindingResults.getAllErrors().stream().map(objectError -> {
			String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
			
			String name = objectError.getObjectName();
			
			if(objectError instanceof FieldError) {
				name =((FieldError) objectError).getField();
			}

			return Problem.Object.builder()
					.name(name)
					.userMessage(message)
					.build();

		}).collect(Collectors.toList());
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(userMessage)
				.objects(problemObjects)
				.build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatExeception(InvalidFormatException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		
		String path = joinPath(e.getPath());
		
		String detail  = String.format("A propriedade '%s' recebeu o valor '%s' "
				+ "que é de um tipo inválido (%s). Corrija e informe um valor compatível com o tipo %s " ,
				path, e.getValue(), e.getValue().getClass().getSimpleName(), e.getTargetType().getSimpleName());
		ProblemType  problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_UI)
				.build();
		
		return handleExceptionInternal(e, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException e,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = joinPath(e.getPath());
		
		String detail  = String.format("A propriedade '%s' não existe. Corrija e tente novamente.",  path);
		ProblemType  problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		
		Problem problem = createProblemBuilder(status, problemType, detail)
				.userMessage(MSG_ERRO_GENERICA_UI)
				.build();
		
		return handleExceptionInternal(e, problem, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail)
				.timestamp(OffsetDateTime.now());	
	}
	
	private String joinPath(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}

}
