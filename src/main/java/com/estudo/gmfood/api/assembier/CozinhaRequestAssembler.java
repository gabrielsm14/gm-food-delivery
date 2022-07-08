package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.CozinhaRequest;
import com.estudo.gmfood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CozinhaRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CozinhaRequest toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaRequest.class);
    }

    public List<CozinhaRequest> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toModel(cozinha))
                .collect(Collectors.toList());
    }
}
