package com.estudo.gmfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FotoProdutoRequest {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
