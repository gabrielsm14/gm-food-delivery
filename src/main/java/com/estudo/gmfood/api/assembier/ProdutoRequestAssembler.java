package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.ProdutoRequest;
import com.estudo.gmfood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoRequest toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoRequest.class);
    }

    public List<ProdutoRequest> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toModel(produto))
                .collect(Collectors.toList());
    }
}
