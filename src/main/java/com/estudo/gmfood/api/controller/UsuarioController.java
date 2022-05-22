package com.estudo.gmfood.api.controller;

import com.estudo.gmfood.api.assembier.UsuarioInputDisassembler;
import com.estudo.gmfood.api.assembier.UsuarioRequestAssembler;
import com.estudo.gmfood.api.model.UsuarioRequest;
import com.estudo.gmfood.api.model.input.UsuarioComSenhaInput;
import com.estudo.gmfood.api.model.input.UsuarioInput;
import com.estudo.gmfood.domain.model.Usuario;
import com.estudo.gmfood.domain.repository.UsuarioRepository;
import com.estudo.gmfood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRequestAssembler usuarioRequestAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public List<UsuarioRequest> listar() {
        List<Usuario> todosUsuarios = usuarioRepository.findAll();

        return usuarioRequestAssembler.toCollectionModel(todosUsuarios);
    }

    @GetMapping("/{id}")
    public UsuarioRequest buscar(@PathVariable Long id) {
        Usuario usuario = usuarioService.buscarOuFalhar(id);

        return usuarioRequestAssembler.toModel(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioRequest adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);

        usuario = usuarioService.salvar(usuario);

        return usuarioRequestAssembler.toModel(usuario);
    }

    @PutMapping("/{id}")
    public UsuarioRequest atualizar(@PathVariable Long id, @ResponseBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(id);

        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

        usuarioAtual = usuarioService.salvar(usuarioAtual);

        return usuarioRequestAssembler.toModel(usuarioAtual);
    }

}
