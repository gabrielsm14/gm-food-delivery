package com.estudo.gmfood.api.assembier;

import com.estudo.gmfood.api.model.PedidoResumoRequest;
import com.estudo.gmfood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResumoRequestAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoRequest toModel(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoRequest.class);
    }

    public List<PedidoResumoRequest> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toModel(pedido))
                .collect(Collectors.toList());
    }
}
