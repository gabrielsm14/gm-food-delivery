package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.CidadeRequest;
import com.estudo.gmfood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeRequest toModel(Cidade cidade) {
        return modelMapper.map(cidade, CidadeRequest.class);
    }

    public List<CidadeRequest> toCollectionModel(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }
}
