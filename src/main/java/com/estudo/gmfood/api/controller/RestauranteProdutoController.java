package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.ProdutoInputDisassembler;
import com.estudo.gmfood.api.assembier.ProdutoRequestAssembler;
import com.estudo.gmfood.api.model.ProdutoRequest;
import com.estudo.gmfood.api.model.input.ProdutoInput;
import com.estudo.gmfood.domain.model.Produto;
import com.estudo.gmfood.domain.model.Restaurante;
import com.estudo.gmfood.domain.repository.ProdutoRepository;
import com.estudo.gmfood.domain.service.RestauranteService;
import com.estudo.gmfood.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurante/{restauranteId}/produtos")
public class RestauranteProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoRequestAssembler produtoRequestAssembler;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @GetMapping
    public List<ProdutoRequest> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        List<Produto> todosProdutos = null;

        if (incluirInativos) {
            todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoRequestAssembler.toCollectionModel(todosProdutos);
    }

    @GetMapping("/{produtoId}")
    public ProdutoRequest buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        return produtoRequestAssembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoRequest adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);

        produto = produtoService.salvar(produto);

        return produtoRequestAssembler.toModel(produto);
    }

    @PutMapping("{produtoId}")
    public ProdutoRequest atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = produtoService.salvar(produtoAtual);

        return produtoRequestAssembler.toModel(produtoAtual);
    }
}
