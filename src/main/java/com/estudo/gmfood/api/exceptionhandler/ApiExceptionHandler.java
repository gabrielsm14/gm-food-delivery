package com.estudo.gmfood.api.exceptionhandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.estudo.gmfood.domain.exception.EntidadeEmUsoException;
import com.estudo.gmfood.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.gmfood.domain.exception.NegocioException;

@ControllerAdvice // diz que dentro desse componente podemos adicionar exception handler que as
					// exceções de todos os controladores serão tratadas por aqui
public class ApiExceptionHandler extends ResponseEntityExceptionHandler { // ResponseEntityExceptionHandler = class de
																			// conveniencia para class handler globais
																			// que é uma implementação padrao que trata
																			// exception internas do spring mvc

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		
		Problem problem = createProblemBuilder(status, ProblemType.MENSAGEM_INCOMPREENSIVEL, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
			WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_NAO_ENCONTRDA, ex.getMessage()).build();
		
//		Problem problem = Problem.builder()
//				.status(status.value())
//				.type("https://gmfood.com.br/entidade-nao-encontrada")
//				.title("Entidade não encontrada")
//				.detail( ex.getMessage())
//				.build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);


	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioExecption(NegocioException ex, WebRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problem problem = createProblemBuilder(status, ProblemType.ERRO_NEGOCIO, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		
		Problem problem = createProblemBuilder(status, ProblemType.ENTIDADE_EM_USO, ex.getMessage()).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if(body == null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
		} else if (body instanceof String) {
			body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
		
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
	
}
