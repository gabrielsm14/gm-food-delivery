package com.estudo.gmfood.api.controller;


import com.estudo.gmfood.api.assembier.RestauranteInputDesassembler;
import com.estudo.gmfood.api.assembier.RestauranteModelAssembler;
import com.estudo.gmfood.api.model.RestauranteRequest;
import com.estudo.gmfood.api.model.input.RestauranteInput;
import com.estudo.gmfood.domain.exception.CidadeNaoEncontradaException;
import com.estudo.gmfood.domain.exception.CozinhaNaoEncontradaException;
import com.estudo.gmfood.domain.exception.NegocioException;
import com.estudo.gmfood.domain.model.Restaurante;
import com.estudo.gmfood.domain.repository.RestauranteRepository;
import com.estudo.gmfood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private SmartValidator validator;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDesassembler restauranteInputDesassembler;

    @GetMapping
    public List<RestauranteRequest> listar() {
        return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @GetMapping("/{id}")
    public RestauranteRequest buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(id);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping
    public RestauranteRequest adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDesassembler.toDomainObject(restauranteInput);

            return restauranteModelAssembler.toModel(restauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteRequest atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteAtual = restauranteService.buscarOuFalhar(id);

            restauranteInputDesassembler.copyToDomainObject(restauranteInput, restauranteAtual);

            return restauranteModelAssembler.toModel(restauranteRepository.save(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId) {
        restauranteService.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId) {
        restauranteService.inativar(restauranteId);
    }
}

