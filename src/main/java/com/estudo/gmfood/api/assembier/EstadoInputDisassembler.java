package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.input.EstadoIdInput;
import com.estudo.gmfood.api.model.input.EstadoInput;
import com.estudo.gmfood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class EstadoInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomainObject(@Valid EstadoIdInput estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }

    public void copyToDomainObject(EstadoInput estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }
}
