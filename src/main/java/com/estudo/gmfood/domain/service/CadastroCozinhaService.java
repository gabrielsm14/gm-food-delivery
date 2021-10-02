package com.estudo.gmfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.estudo.gmfood.domain.exception.EntidadeEmUsoException;
import com.estudo.gmfood.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.gmfood.domain.model.Cozinha;
import com.estudo.gmfood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	 
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public void excluir(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e ) {
			throw new  EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de cozinha com código %d", id));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso", id));
		}
	}
}
