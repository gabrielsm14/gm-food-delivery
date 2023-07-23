package com.estudo.gmfood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoRequest {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Boolean ativo;
}
