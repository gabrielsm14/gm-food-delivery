package com.estudo.gmfood.domain.service;

import com.estudo.gmfood.domain.exception.EntidadeEmUsoException;
import com.estudo.gmfood.domain.exception.GrupoNaoEncontradaException;
import com.estudo.gmfood.domain.model.Grupo;
import com.estudo.gmfood.domain.model.Permissao;
import com.estudo.gmfood.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GrupoService {

    private static final String MSG_FORMA_PAGAMENTO_EM_USO = "Grupo de código %d não pode ser removida, pois está em uso";

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private PermissaoService permissaoService;

    @Transactional
    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradaException(id);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
        }
    }

    @Transactional
    public void desassociarPermissao(Long grupoId, Long permissaoId) {
        Grupo grupo = buscarOuFalhar(grupoId);

        Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);

        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void associarPermissao(Long grupoId, Long pemissaoId) {
        Grupo grupo = buscarOuFalhar(grupoId);

        Permissao permissao = permissaoService.buscarOuFalhar(pemissaoId);

        grupo.adicionarPermissao(permissao);
    }

    public Grupo buscarOuFalhar(Long id) {
       return grupoRepository.findById(id)
               .orElseThrow(() -> new GrupoNaoEncontradaException(id));
    }
}
