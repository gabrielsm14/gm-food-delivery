package com.estudo.gmfood.domain.service;

import com.estudo.gmfood.domain.exception.PedidoNaoEncontradoException;
import com.estudo.gmfood.domain.model.Pedido;
import com.estudo.gmfood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }
}
