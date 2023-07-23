package com.estudo.gmfood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EstadoInput {

    @NotBlank
    private String nome;
}
