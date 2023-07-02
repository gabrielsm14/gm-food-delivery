package com.estudo.gmfood.domain.service;

import com.estudo.gmfood.domain.exception.NegocioException;
import com.estudo.gmfood.domain.exception.UsuarioNaoEncontradaException;
import com.estudo.gmfood.domain.model.Grupo;
import com.estudo.gmfood.domain.model.Permissao;
import com.estudo.gmfood.domain.model.Usuario;
import com.estudo.gmfood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removida, pois está em uso";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoService grupoService;

//    @Transactional
//    public Usuario salvar(Usuario usuario) {
//        usuarioRepository.detach(usuario);
//
//        Optional<Usuario> usuarioExistente = usuarioRepository.finByEmail(usuario.getEmail());
//
//        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
//            throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
//        }
//
//        return usuarioRepository.save(usuario);
//    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(id);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário");
        }

        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        usuario.removerGrupo(grupo);
    }

    @Transactional
    public void associarGrupo(Long  usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        usuario.adicionarGrupo(grupo);
    }

    public Usuario buscarOuFalhar(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradaException(id));
    }
}
