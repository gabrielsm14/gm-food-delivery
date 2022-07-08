package com.estudo.gmfood.domain.service;

import com.estudo.gmfood.domain.exception.PermissaoNaoEncontradaException;
import com.estudo.gmfood.domain.model.Permissao;
import com.estudo.gmfood.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId).orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}
