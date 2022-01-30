package com.estudo.gmfood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.gmfood.domain.exception.CozinhaNaoEncontradaException;
import com.estudo.gmfood.domain.exception.NegocioException;
import com.estudo.gmfood.domain.model.Restaurante;
import com.estudo.gmfood.domain.repository.RestauranteRepository;
import com.estudo.gmfood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}

	@GetMapping("/{id}")
	public Restaurante buscar(@PathVariable Long id) {
		return cadastroRestauranteService.buscarOuFalhar(id);

	}

	@PostMapping
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		try {
			
			return cadastroRestauranteService.salvar(restaurante);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public Restaurante atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {

		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);

		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
				"produtos");
		try {
			
			return restauranteRepository.save(restauranteAtual);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}


	}

	@PatchMapping("/{id}")
	public Restaurante atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {

		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(id);

		merge(campos, restauranteAtual);

		return atualizar(id, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteeAtual) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

		System.out.println(restauranteOrigem);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade); // findField retorna a
																							// instancia de um campo
			field.setAccessible(true);

			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem); // getField buscar o valor do campo

//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

			ReflectionUtils.setField(field, restauranteeAtual, novoValor);
		});
	}

}
