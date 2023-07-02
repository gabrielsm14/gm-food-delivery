package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.PedidoInputDisassembler;
import com.estudo.gmfood.api.assembier.PedidoRequestAssembler;
import com.estudo.gmfood.api.assembier.PedidoResumoRequestAssembler;
import com.estudo.gmfood.api.model.PedidoRequest;
import com.estudo.gmfood.api.model.PedidoResumoRequest;
import com.estudo.gmfood.core.data.PageableTranslator;
import com.estudo.gmfood.domain.model.Pedido;
import com.estudo.gmfood.domain.repository.PedidoRepository;
import com.estudo.gmfood.domain.filter.PedidoFilter;
import com.estudo.gmfood.domain.service.EmissaoPedidoService;
import com.estudo.gmfood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    public Page<PedidoResumoRequest> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable page) {
        page = traduzirPageable(page);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), page);

        List<PedidoResumoRequest> pedidosResumoRequest = pedidoResumoRequestAssembler.toCollectionModel(pedidosPage.getContent());

        Page<PedidoResumoRequest> cozinhasRequestPage = new PageImpl<>(pedidosResumoRequest, page, pedidosPage.getTotalElements());

        return cozinhasRequestPage;
    }

    @GetMapping("{/codigoPedido}")
    public PedidoRequest buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

        return pedidoRequestAssembler.toModel(pedido);
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public PedidoRequest adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
//        try {
//            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
//
//            // TODO pegar usuário autenticado
//            novoPedido.setCliente(new Usuario());
//            novoPedido.getCliente().setId(1L);
//
//            novoPedido = emissaoPedidoService.emitir(novoPedido);
//
//            return pedidoRequestAssembler.toModel(novoPedido);
//        } catch (EntidadeNaoEncontradaException e) {
//            throw new NegocioException(e.getMessage(), e);
//        }
//    }

    private Pageable traduzirPageable(Pageable apiPage) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "cliente", "cliente.nome",
                "valorTotal", "valorTotal");

        return PageableTranslator.translate(apiPage, mapeamento);
    }
}
