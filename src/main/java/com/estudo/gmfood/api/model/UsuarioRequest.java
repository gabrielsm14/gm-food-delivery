package com.estudo.gmfood.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UsuarioRequest {

    private Long id;
    private String nome;
    private String email;
}
