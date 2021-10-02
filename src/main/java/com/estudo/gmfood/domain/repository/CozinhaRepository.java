package com.estudo.gmfood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.gmfood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

	List<Cozinha> findTodasByNome(String nome);
	
	Optional<Cozinha> findByNome(String nome);

	List<Cozinha> findTodasByNomeContaining(String nome); // Containing coloca o "% like %"
	
	boolean existsByNome(String nome);
}
