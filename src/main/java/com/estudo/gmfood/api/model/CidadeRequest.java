package com.estudo.gmfood.api.model;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class CidadeRequest {

    private Long id;
    private String nome;
    private EstadoRequest estado;
}
