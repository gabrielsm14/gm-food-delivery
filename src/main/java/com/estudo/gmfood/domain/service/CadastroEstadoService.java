package com.estudo.gmfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.estudo.gmfood.domain.exception.EntidadeEmUsoException;
import com.estudo.gmfood.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.gmfood.domain.model.Estado;
import com.estudo.gmfood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public void excluir(Long id) {
		try {
			estadoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de estado com o código %d", id));
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está em uso", id));
		}
	}
}
