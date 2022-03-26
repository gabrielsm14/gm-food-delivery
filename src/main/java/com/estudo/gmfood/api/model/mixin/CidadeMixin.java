package com.estudo.gmfood.api.model.mixin;

import com.estudo.gmfood.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CidadeMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Estado estado;
}
