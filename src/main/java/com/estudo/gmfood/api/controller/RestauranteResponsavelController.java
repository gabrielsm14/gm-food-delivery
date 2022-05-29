package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.UsuarioRequestAssembler;
import com.estudo.gmfood.api.model.UsuarioRequest;
import com.estudo.gmfood.domain.model.Restaurante;
import com.estudo.gmfood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteResponsavelController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private UsuarioRequestAssembler usuarioRequestAssembler;

    @GetMapping
    public List<UsuarioRequest> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        return usuarioRequestAssembler.toCollectionModel(restaurante.getResponsaveis());
    }

    @DeleteMapping("/{restauranteId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long restauranteId, Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }

    @PutMapping("/{permissaoId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long restauranteId, Long usuarioId) {
        restauranteService.desassociarResponsavel(restauranteId, usuarioId);
    }
}
