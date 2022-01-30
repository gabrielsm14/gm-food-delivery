package com.estudo.gmfood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudo.gmfood.domain.exception.RestauranteNaoEncontradaException;
import com.estudo.gmfood.domain.model.Cozinha;
import com.estudo.gmfood.domain.model.Restaurante;
import com.estudo.gmfood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {


	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	CadastroCozinhaService cadastroCozinhaService;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);

		restaurante.setCozinha(cozinha);

		return restauranteRepository.save(restaurante);
	}

	public Restaurante buscarOuFalhar(Long id) {
		return restauranteRepository.findById(id).orElseThrow(
				() -> new RestauranteNaoEncontradaException(id));
	}

}
