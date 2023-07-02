package com.estudo.gmfood.domain.service;

import com.estudo.gmfood.domain.exception.ProdutoNaoEncontradaException;
import com.estudo.gmfood.domain.model.Produto;
import com.estudo.gmfood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    private static final String MSG_USUARIO_EM_USO = "Produto de código %d não pode ser removida, pois está em uso";

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

//    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
//        return produtoRepository.findById(restauranteId, produtoId).orElseThrow(() -> new ProdutoNaoEncontradaException(restauranteId, produtoId));
//    }
}
