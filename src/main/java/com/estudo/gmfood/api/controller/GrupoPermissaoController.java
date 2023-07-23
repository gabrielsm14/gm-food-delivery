package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.PermissaoRequestAssembler;
import com.estudo.gmfood.api.model.PermissaoRequest;
import com.estudo.gmfood.domain.model.Grupo;
import com.estudo.gmfood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private PermissaoRequestAssembler permissaoRequestAssembler;

    public List<PermissaoRequest> listar(@PathVariable Long grupoId) {
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        return permissaoRequestAssembler.toCollectionModel(grupo.getPermissaos());

    }

    @DeleteMapping("/{permissaoId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long grupoId, Long permissaoId) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }

    @PutMapping("/{permissaoId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long grupoId, Long permissaoId) {
        grupoService.associarPermissao(grupoId, permissaoId);
    }
}
