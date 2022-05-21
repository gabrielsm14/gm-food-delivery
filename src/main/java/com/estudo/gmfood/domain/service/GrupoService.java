package com.estudo.gmfood.domain.service;

import com.estudo.gmfood.domain.exception.EntidadeEmUsoException;
import com.estudo.gmfood.domain.exception.GrupoNaoEncontradaException;
import com.estudo.gmfood.domain.model.Grupo;
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

    public Grupo buscarOuFalhar(Long id) {
       return grupoRepository.findById(id)
               .orElseThrow(() -> new GrupoNaoEncontradaException(id));
    }
}
