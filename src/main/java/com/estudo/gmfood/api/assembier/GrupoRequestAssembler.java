package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.GrupoRequest;
import com.estudo.gmfood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoRequest toModel(Grupo grupo) {
        return modelMapper.map(grupo, GrupoRequest.class);
    }

    public List<GrupoRequest> toCollectionModel(Collection<Grupo> grupos) {
        return grupos.stream()
                .map(grupo -> toModel(grupo))
                .collect(Collectors.toList());
    }
}
