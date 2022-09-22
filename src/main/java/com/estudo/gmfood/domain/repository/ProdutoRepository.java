package com.estudo.gmfood.domain.repository;

import com.estudo.gmfood.domain.model.Produto;
import com.estudo.gmfood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("from Produto where restaurante.id = restaurante and id = :produto")
    Optional<Produto> findById(@Param("restaurante") Long restauranteId, @Param("produto") Long produtoId);

    List<Produto> findTodosByRestaurante(Restaurante restaurante);

    @Query("from Produto p where p.ativo = true and p.restaurantes = :restaurante")
    List<Produto> findAtivosByRestaurantes(Restaurante restaurantes);
}
