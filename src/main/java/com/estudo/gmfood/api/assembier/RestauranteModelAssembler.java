package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.RestauranteRequest;
import com.estudo.gmfood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteRequest toModel(Restaurante restaurante) {
       return modelMapper.map(restaurante, RestauranteRequest.class);
    }

    public List<RestauranteRequest> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModel(restaurante))
                .collect(Collectors.toList());
    }
}
