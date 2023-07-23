package com.estudo.gmfood.domain.service;

import com.estudo.gmfood.domain.model.FotoProduto;
import com.estudo.gmfood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalagoFotoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto) {
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(
                fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId());

        if (fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
        }

        return produtoRepository.save(fotoProduto);
    }
}
