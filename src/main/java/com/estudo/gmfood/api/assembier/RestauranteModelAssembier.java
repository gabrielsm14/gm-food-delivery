package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.CozinhaRequest;
import com.estudo.gmfood.api.model.RestauranteRequest;
import com.estudo.gmfood.domain.model.Restaurante;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteModelAssembier {

    public RestauranteRequest toModel(Restaurante restaurante) {
        CozinhaRequest cozinhaRequest = new CozinhaRequest();
        cozinhaRequest.setId(restaurante.getCozinha().getId());
        cozinhaRequest.setNome(restaurante.getCozinha().getNome());

        RestauranteRequest restauranteRequest = new RestauranteRequest(); // conversao da entidade para restaurante
        restauranteRequest.setId(restaurante.getId());
        restauranteRequest.setNome(restaurante.getNome());
        restauranteRequest.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteRequest.setCozinha(cozinhaRequest);
        return restauranteRequest;
    }

    public List<RestauranteRequest> toCollectionModel(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toModel(restaurante))
                .collect(Collectors.toList());
    }
}
