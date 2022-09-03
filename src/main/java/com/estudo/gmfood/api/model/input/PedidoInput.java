package com.estudo.gmfood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class PedidoInput {

    @Valid
    @NotNull
    private RestauranteIdInput restaurante;

    @Valid
    @NotNull
    private EnderecoInput enderecoInput;

    @Valid
    @NotNull
    private FormaPagamentoIdInput formaPagamento;

    private List<ItemPedidoInput> itens;
}
