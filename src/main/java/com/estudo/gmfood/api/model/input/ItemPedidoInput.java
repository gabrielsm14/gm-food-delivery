package com.estudo.gmfood.api.model.input;

import com.sun.istack.NotNull;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

public class ItemPedidoInput {

    @Valid
    @NotNull
    private RestauranteIdInput restaurante;

    @Valid
    @NotNull
    private EnderecoInput enderecoEntrega;

    @Valid
    @NotNull
    private FormaPagamentoIdInput formaPagamento;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoInput> itens;
}
