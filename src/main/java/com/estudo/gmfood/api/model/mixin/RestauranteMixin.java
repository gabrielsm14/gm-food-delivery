package com.estudo.gmfood.api.model.mixin;

import com.estudo.gmfood.core.validation.Groups;
import com.estudo.gmfood.domain.model.Cozinha;
import com.estudo.gmfood.domain.model.Endereco;
import com.estudo.gmfood.domain.model.FormaPagamento;
import com.estudo.gmfood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private LocalDateTime dataCadastro;

    @JsonIgnore
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento;

    @JsonIgnore
    private List<Produto> produtos;
}
