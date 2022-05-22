package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.UsuarioRequest;
import com.estudo.gmfood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioRequest toModel(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioRequest.class);
    }

    public List<UsuarioRequest> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toModel(usuario))
                .collect(Collectors.toList());
    }
}
