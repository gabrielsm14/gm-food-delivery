package com.estudo.gmfood.api.exceptionhandler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex,
			WebRequest request) {

		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);

//		Problema problema = Problema.builder()
//				.dataHora(LocalDateTime.now())
//				.mensagem(e.getMessage()).build();
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body(problema);

	}

	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> tratarNegocioExecption(NegocioException ex, WebRequest request) {

		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

//		Problema problema = Problema.builder()
//				.dataHora(LocalDateTime.now())
//				.mensagem(e.getMessage()).build();
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(problema);
	}

	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> tratarEntidadeEmUsoException(EntidadeEmUsoException ex, WebRequest request) {

		return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);

//		Problema problema = Problema.builder()
//				.dataHora(LocalDateTime.now())
//				.mensagem(e.getMessage()).build();
//		return ResponseEntity.status(HttpStatus.CONFLICT).body(problema);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if(body == null) {
			body = Problema.builder()
					.dataHora(LocalDateTime.now())
					.mensagem(status.getReasonPhrase())
					.build();
		} else if (body instanceof String) {
			body = Problema.builder()
					.dataHora(LocalDateTime.now())
					.mensagem((String) body)
					.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

//	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//	public ResponseEntity<?> tratarHttpMediaTypeNotSupportedException() {
//		Problema problema = Problema.builder()
//				.dataHora(LocalDateTime.now())
//				.mensagem("O tipo de mídia não é aceito").build();
//		
//		return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(problema);
//	}
}
