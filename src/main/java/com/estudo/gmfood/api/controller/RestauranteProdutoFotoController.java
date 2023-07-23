package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.FotoProdutoRequestAssembler;
import com.estudo.gmfood.api.model.FotoProdutoRequest;
import com.estudo.gmfood.api.model.input.FotoProdutoInput;
import com.estudo.gmfood.domain.model.FotoProduto;
import com.estudo.gmfood.domain.model.Produto;
import com.estudo.gmfood.domain.service.CatalagoFotoProdutoService;
import com.estudo.gmfood.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    @Autowired
    private ProdutoService cadastroProdutoService;

    @Autowired
    private CatalagoFotoProdutoService catalagoFotoProdutoService;

    @Autowired
    private FotoProdutoRequestAssembler fotoProdutoRequestAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoRequest atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {

        Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(fotoProdutoInput.getArquivo().getContentType());
        fotoProduto.setTamanho(fotoProdutoInput.getArquivo().getSize());
        fotoProduto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());

        FotoProduto salvarFoto = catalagoFotoProdutoService.salvar(fotoProduto);

        return fotoProdutoRequestAssembler.toModel(salvarFoto);
    }
}
