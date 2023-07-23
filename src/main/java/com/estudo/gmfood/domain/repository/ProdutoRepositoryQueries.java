package com.estudo.gmfood.domain.repository;

import com.estudo.gmfood.domain.model.FotoProduto;
import com.estudo.gmfood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto fotoProduto);

	void delete(FotoProduto fotoProduto);
}