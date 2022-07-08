package com.estudo.gmfood.api.controller;

import java.util.List;

import com.estudo.gmfood.api.assembier.EstadoInputDisassembler;
import com.estudo.gmfood.api.assembier.EstadoRequestAssembler;
import com.estudo.gmfood.api.model.EstadoRequest;
import com.estudo.gmfood.api.model.input.EstadoIdInput;
import com.estudo.gmfood.api.model.input.EstadoInput;
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

import com.estudo.gmfood.domain.model.Estado;
import com.estudo.gmfood.domain.repository.EstadoRepository;
import com.estudo.gmfood.domain.service.CadastroEstadoService;

import javax.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoRequestAssembler estadoModelAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;

	@GetMapping
	public List<EstadoRequest> listar() {
		List<Estado> todosEstados = estadoRepository.findAll();

		return estadoModelAssembler.toCollectionModel(todosEstados);

	}
	
	@GetMapping("/{estadoId}")
	public EstadoRequest buscar(@PathVariable Long estadoId) {
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);

		return estadoModelAssembler.toModel(estado);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoRequest adicionar(@RequestBody @Valid EstadoIdInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);

		estado = cadastroEstado.salvar(estado);

		return estadoModelAssembler.toModel(estado);
	}
	
	@PutMapping("/{estadoId}")
	public EstadoRequest atualizar(@PathVariable Long estadoId,
							@RequestBody @Valid EstadoInput estadoInput) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

		estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

		estadoAtual = cadastroEstado.salvar(estadoAtual);

		return estadoModelAssembler.toModel(estadoAtual);
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.excluir(estadoId);	
	}
	
}