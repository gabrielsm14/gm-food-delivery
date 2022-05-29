package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.GrupoRequestAssembler;
import com.estudo.gmfood.api.model.GrupoRequest;
import com.estudo.gmfood.domain.model.Usuario;
import com.estudo.gmfood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoRequestAssembler grupoRequestAssembler;

    public List<GrupoRequest> listar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        return grupoRequestAssembler.toCollectionModel(usuario.getGrupos());

    }

    @DeleteMapping("/{usuarioId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociar(@PathVariable Long usuarioId, Long grupoId) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{usuarioId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associar(@PathVariable Long usuarioId, Long grupoId) {
        usuarioService.associarGrupo(usuarioId, grupoId);
    }
}
