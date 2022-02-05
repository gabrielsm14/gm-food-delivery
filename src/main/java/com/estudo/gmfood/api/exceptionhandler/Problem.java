package com.estudo.gmfood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@JsonInclude(Include.NON_NULL) // Só inclua na representação json se o valor da propriedade nao estiver NULL
@Getter
@Builder
public class Problem {
	
	private Integer status; // Codigo da resposta 
	private String type; // é uma URI que especifica o tipo de problema
	private String title; //é uma descricao do tipo do porblema, descricao mais legivel 
	private String detail; // é uma descricao especifica sobre a ocorrencia do erro, descriao mais detalhada da ocorrencia

	private String userMessage;
	private LocalDateTime timestamp;
}
