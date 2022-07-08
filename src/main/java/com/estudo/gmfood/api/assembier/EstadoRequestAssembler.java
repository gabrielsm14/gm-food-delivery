package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.EstadoRequest;
import com.estudo.gmfood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoRequest toModel(Estado estado) {
        return modelMapper.map(estado, EstadoRequest.class);
    }

    public List<EstadoRequest> toCollectionModel(List<Estado> estados) {
        return estados.stream()
                .map(estado -> toModel(estado))
                .collect(Collectors.toList());
    }
}
