package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.input.RestauranteInput;
import com.estudo.gmfood.domain.model.Cidade;
import com.estudo.gmfood.domain.model.Cozinha;
import com.estudo.gmfood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDesassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
       return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
//        Para evitar org.hibernate.HibernateException: identifier of an instance of com.estudo.gmfood.domain.cozina was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());

        if (restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }

        modelMapper.map(restauranteInput, restaurante);
    }

}
