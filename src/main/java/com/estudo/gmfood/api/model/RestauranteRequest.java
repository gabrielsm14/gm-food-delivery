package com.estudo.gmfood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestauranteRequest {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaRequest cozinha;
    private Boolean ativo;
    private EnderecoRequest endereco;
    private Boolean aberto;
}
