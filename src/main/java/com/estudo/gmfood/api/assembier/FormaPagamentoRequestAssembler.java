package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.EstadoRequest;
import com.estudo.gmfood.api.model.FormaPagamentoRequest;
import com.estudo.gmfood.domain.model.Estado;
import com.estudo.gmfood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoRequest toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoRequest.class);
    }

    public List<FormaPagamentoRequest> toCollectionModel(List<FormaPagamento> formaPagamentos) {
        return formaPagamentos.stream()
                .map(formaPagamento -> toModel(formaPagamento))
                .collect(Collectors.toList());
    }
}
