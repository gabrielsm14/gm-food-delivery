package com.estudo.gmfood.api.controller;


import com.estudo.gmfood.api.assembier.RestauranteInputDesassembler;
import com.estudo.gmfood.api.assembier.RestauranteModelAssembler;
import com.estudo.gmfood.api.model.RestauranteRequest;
import com.estudo.gmfood.api.model.input.RestauranteInput;
import com.estudo.gmfood.domain.exception.CozinhaNaoEncontradaException;
import com.estudo.gmfood.domain.exception.NegocioException;
import com.estudo.gmfood.domain.model.Restaurante;
import com.estudo.gmfood.domain.repository.RestauranteRepository;
import com.estudo.gmfood.domain.service.CadastroRestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CadastroRestauranteService cadastroRestauranteService;

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
        Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(id);

        return restauranteModelAssembler.toModel(restaurante);
    }

    @PostMapping
    public RestauranteRequest adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDesassembler.toDomainObject(restauranteInput);

            return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public RestauranteRequest atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDesassembler.toDomainObject(restauranteInput);

            Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);

            BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return restauranteModelAssembler.toModel(restauranteRepository.save(restauranteAtual));
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
