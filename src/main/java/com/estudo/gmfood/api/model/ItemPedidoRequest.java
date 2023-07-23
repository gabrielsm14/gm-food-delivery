package com.estudo.gmfood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemPedidoRequest {

    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;
}
