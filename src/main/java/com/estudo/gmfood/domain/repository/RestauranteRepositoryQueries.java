package com.estudo.gmfood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.estudo.gmfood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> ConsultaPorNomeTaxaFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

	List<Restaurante> findComFreteGratis(String nome);
}