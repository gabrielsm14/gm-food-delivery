package com.estudo.gmfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoRequest {

    private Long id;
    private String nome;
    private String estado;
}
