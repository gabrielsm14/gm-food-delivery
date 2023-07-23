package com.estudo.gmfood.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EnderecoRequest {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private CidadeResumoRequest cidade;
}
