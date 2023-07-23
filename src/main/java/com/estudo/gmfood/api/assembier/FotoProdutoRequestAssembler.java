package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.FotoProdutoRequest;
import com.estudo.gmfood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FotoProdutoRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoRequest toModel(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoRequest.class);
    }
}
