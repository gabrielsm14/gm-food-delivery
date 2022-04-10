package com.estudo.gmfood.api.controller;

import java.util.List;

import com.estudo.gmfood.api.assembier.CozinhaInputDisassembler;
import com.estudo.gmfood.api.assembier.CozinhaRequestAssembler;
import com.estudo.gmfood.api.model.CozinhaRequest;
import com.estudo.gmfood.api.model.input.CozinhaInput;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.gmfood.domain.model.Cozinha;
import com.estudo.gmfood.domain.repository.CozinhaRepository;
import com.estudo.gmfood.domain.service.CadastroCozinhaService;

import javax.validation.Valid;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Autowired
	private CozinhaRequestAssembler cozinhaRequestAssembler;

	@Autowired
	private CozinhaInputDisassembler  cozinhaInputDisassembler ;

	@GetMapping
	public List<CozinhaRequest> listar() {
		List<Cozinha> todasCozinhas = cozinhaRepository.findAll();

		return cozinhaRequestAssembler.toCollectionModel(todasCozinhas);	}

	@GetMapping("/{id}")
	public CozinhaRequest buscar(@PathVariable Long id) {
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(id);

		return cozinhaRequestAssembler.toModel(cozinha);
	}

	@PostMapping
	public CozinhaRequest adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		cozinha = cadastroCozinhaService.salvar(cozinha);

		return cozinhaRequestAssembler.toModel(cozinha);
	}

	@PutMapping("/{id}")
	public CozinhaRequest atualizar(@PathVariable Long id, @RequestBody CozinhaInput cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(id);
		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);

		return cozinhaRequestAssembler.toModel(cozinhaAtual);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCozinhaService.excluir(id);

	}

}
