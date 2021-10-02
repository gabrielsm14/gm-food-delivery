package com.estudo.gmfood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.gmfood.domain.exception.EntidadeEmUsoException;
import com.estudo.gmfood.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.gmfood.domain.model.Cozinha;
import com.estudo.gmfood.domain.repository.CozinhaRepository;
import com.estudo.gmfood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	CadastroCozinhaService cadastroCozinhaService;

	@GetMapping
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}


	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);

		if(cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}

		return ResponseEntity.notFound().build();

//		return ResponseEntity.status(HttpStatus.OK).body(cozinha);

//		HttpHeaders headers = new HttpHeaders();
//		headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");

//		return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();

	}

	@PostMapping
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
		Cozinha salvar = cadastroCozinhaService.salvar(cozinha);

		return ResponseEntity.status(HttpStatus.CREATED).body(salvar);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(id);

		if (cozinhaAtual.isPresent()) {

//		    cozinhaAtual.setNome(cozinha.getNome());
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id"); // copiar as propriedades de cozinha para dentro de cozinha atual

			Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual.get());
			return ResponseEntity.ok(cozinhaSalva);
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long id) {
		try {
			cadastroCozinhaService.excluir(id);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
