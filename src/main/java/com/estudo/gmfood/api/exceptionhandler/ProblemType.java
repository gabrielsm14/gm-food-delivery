package com.estudo.gmfood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	ENTIDADE_NAO_ENCONTRDA("/entidade-nao-encontrada", "Entidade não encontrada");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://gmfood.com.br" + path;
		this.title = title;
	}
}
