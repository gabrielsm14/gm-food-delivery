package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.PedidoRequest;
import com.estudo.gmfood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoRequest toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoRequest.class);
    }

    public List<PedidoRequest> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
}
