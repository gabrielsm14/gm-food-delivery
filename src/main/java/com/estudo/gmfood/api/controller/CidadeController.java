package com.estudo.gmfood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.estudo.gmfood.api.assembier.CidadeInputDisassembler;
import com.estudo.gmfood.api.assembier.CidadeRequestAssembler;
import com.estudo.gmfood.api.model.CidadeRequest;
import com.estudo.gmfood.api.model.input.CidadeInput;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.gmfood.domain.exception.EstadoNaoEncontradaException;
import com.estudo.gmfood.domain.exception.NegocioException;
import com.estudo.gmfood.domain.model.Cidade;
import com.estudo.gmfood.domain.repository.CidadeRepository;
import com.estudo.gmfood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeRequestAssembler cidadeRequestAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@GetMapping
	public List<CidadeRequest> listar() {
		List<Cidade> todasCidades = cidadeRepository.findAll();

		return cidadeRequestAssembler.toCollectionModel(todasCidades);	}

	@GetMapping("/{cidadeId}")
	public CidadeRequest buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);

		return cidadeRequestAssembler.toModel(cidade);	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeRequest adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);

			cidade = cadastroCidade.salvar(cidade);

			return cidadeRequestAssembler.toModel(cidade);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{cidadeId}")
	public CidadeRequest atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInput cidadeInput) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			cidadeAtual = cadastroCidade.salvar(cidadeAtual);

			return cidadeRequestAssembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}

}