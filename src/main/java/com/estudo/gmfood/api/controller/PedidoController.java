package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.PedidoRequestAssembler;
import com.estudo.gmfood.api.model.PedidoRequest;
import com.estudo.gmfood.domain.model.Pedido;
import com.estudo.gmfood.domain.repository.PedidoRepository;
import com.estudo.gmfood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoRequestAssembler pedidoRequestAssembler;

    @GetMapping
    public List<PedidoRequest> listar() {
        List<Pedido> todosPedidos = pedidoRepository.findAll();

        return pedidoRequestAssembler.toCollectionModel(todosPedidos);
    }

    @GetMapping("{/id}")
    public PedidoRequest buscar(@PathVariable Long id) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(id);

        return pedidoRequestAssembler.toModel(pedido);
    }
}
