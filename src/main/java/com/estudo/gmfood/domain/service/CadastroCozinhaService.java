package com.estudo.gmfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.estudo.gmfood.domain.exception.CozinhaNaoEncontradaException;
import com.estudo.gmfood.domain.exception.EntidadeEmUsoException;
import com.estudo.gmfood.domain.model.Cozinha;
import com.estudo.gmfood.domain.repository.CozinhaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}

	@Transactional
	public void excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);
			cozinhaRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}

	public Cozinha buscarOuFalhar(Long id) {
		return cozinhaRepository.findById(id).orElseThrow(() -> new CozinhaNaoEncontradaException(id));
	}

}
