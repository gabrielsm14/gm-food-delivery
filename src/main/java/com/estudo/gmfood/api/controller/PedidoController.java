package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.PedidoInputDisassembler;
import com.estudo.gmfood.api.assembier.PedidoRequestAssembler;
import com.estudo.gmfood.api.assembier.PedidoResumoRequestAssembler;
import com.estudo.gmfood.api.model.PedidoRequest;
import com.estudo.gmfood.api.model.PedidoResumoRequest;
import com.estudo.gmfood.api.model.input.PedidoInput;
import com.estudo.gmfood.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.gmfood.domain.exception.NegocioException;
import com.estudo.gmfood.domain.model.Pedido;
import com.estudo.gmfood.domain.model.Usuario;
import com.estudo.gmfood.domain.repository.PedidoRepository;
import com.estudo.gmfood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @Autowired
    private PedidoResumoRequestAssembler pedidoResumoRequestAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public List<PedidoResumoRequest> listar() {
        List<Pedido> todosPedidos = pedidoRepository.findAll();

        return pedidoResumoRequestAssembler.toCollectionModel(todosPedidos);
    }

    @GetMapping("{/id}")
    public PedidoRequest buscar(@PathVariable Long id) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(id);

        return pedidoRequestAssembler.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoRequest adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            // TODO pegar usuário autenticado
            novoPedido.setCliente(new Usuario());
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);

            return pedidoRequestAssembler.toModel(novoPedido);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
