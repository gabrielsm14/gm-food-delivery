package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.FormaPagamentoInputDisassembler;
import com.estudo.gmfood.api.assembier.FormaPagamentoRequestAssembler;
import com.estudo.gmfood.api.model.FormaPagamentoRequest;
import com.estudo.gmfood.api.model.input.FormaPagamentoInput;
import com.estudo.gmfood.domain.model.FormaPagamento;
import com.estudo.gmfood.domain.repository.FormaPagamentoRepository;
import com.estudo.gmfood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoRequestAssembler formaPagamentoRequestAssembler;

    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @GetMapping
    public List<FormaPagamentoRequest> listar() {
        List<FormaPagamento> todasFormaPagamentos = formaPagamentoRepository.findAll();

        return formaPagamentoRequestAssembler.toCollectionModel(todasFormaPagamentos);
    }

    @GetMapping("/{id")
    public FormaPagamentoRequest buscar(@PathVariable Long id) {
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(id);

        return formaPagamentoRequestAssembler.toModel(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoRequest adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

        formaPagamento = formaPagamentoService.salvar(formaPagamento);

        return formaPagamentoRequestAssembler.toModel(formaPagamento);
    }

    @PutMapping("/{id}")
    public FormaPagamentoRequest atualizar(@PathVariable Long id, @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(id);

        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

        formaPagamentoAtual = formaPagamentoService.salvar(formaPagamentoAtual);

        return formaPagamentoRequestAssembler.toModel(formaPagamentoAtual);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        formaPagamentoService.excluir(id);

    }

}
