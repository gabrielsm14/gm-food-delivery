package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.GrupoInputDisassembler;
import com.estudo.gmfood.api.assembier.GrupoRequestAssembler;
import com.estudo.gmfood.api.model.GrupoRequest;
import com.estudo.gmfood.api.model.input.GrupoInput;
import com.estudo.gmfood.domain.model.Grupo;
import com.estudo.gmfood.domain.repository.GrupoRepository;
import com.estudo.gmfood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private GrupoRequestAssembler grupoRequestAssembler;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @GetMapping
    public List<GrupoRequest> listar() {
        List<Grupo> todosGrupos = grupoRepository.findAll();

        return grupoRequestAssembler.toCollectionModel(todosGrupos);
    }

    @GetMapping("/{id}")
    public GrupoRequest buscar(@PathVariable Long id) {
        Grupo grupo = grupoService.buscarOuFalhar(id);

        return grupoRequestAssembler.toModel(grupo);
    }

    @PostMapping
    public GrupoRequest adicionar(@RequestBody GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);

        grupo = grupoService.salvar(grupo);

        return grupoRequestAssembler.toModel(grupo);
    }
}
