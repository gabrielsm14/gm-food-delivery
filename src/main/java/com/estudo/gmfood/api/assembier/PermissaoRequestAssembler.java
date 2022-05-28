package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.PermissaoRequest;
import com.estudo.gmfood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoRequest toModel(Permissao permissao) {
        return modelMapper.map(permissao, PermissaoRequest.class);
    }

    public List<PermissaoRequest> toCollectionModel(Collection<Permissao> permissaos) {
        return permissaos.stream()
                .map(permissao -> toModel(permissao))
                .collect(Collectors.toList());
    }
}
