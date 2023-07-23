package com.estudo.gmfood.infrastructure.repository;

import com.estudo.gmfood.domain.model.FotoProduto;
import com.estudo.gmfood.domain.repository.ProdutoRepository;
import com.estudo.gmfood.domain.repository.ProdutoRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto fotoProduto) {
		return manager.merge(fotoProduto);
	}

	@Transactional
	@Override
	public void delete(FotoProduto fotoProduto) {
		manager.remove(fotoProduto);
	}
}
