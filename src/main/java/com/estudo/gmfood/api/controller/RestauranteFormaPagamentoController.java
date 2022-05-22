package com.estudo.gmfood.api.controller;


import com.estudo.gmfood.api.assembier.FormaPagamentoRequestAssembler;
import com.estudo.gmfood.api.model.FormaPagamentoRequest;
import com.estudo.gmfood.domain.model.Restaurante;
import com.estudo.gmfood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauramteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

    @Autowired
    private FormaPagamentoRequestAssembler formaPagamentoRequestAssembler;

    @GetMapping
    public List<FormaPagamentoRequest> listar(@PathVariable Long restauramteId) {
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauramteId);

        return formaPagamentoRequestAssembler.toCollectionModel(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, Long formaPagamentoId) {
        cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, Long formaPagamentoId) {
        cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }

}
