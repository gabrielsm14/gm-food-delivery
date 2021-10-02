package com.estudo.gmfood.api.controller;

import java.util.List;
import java.util.Optional;

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
import com.estudo.gmfood.domain.model.Estado;
import com.estudo.gmfood.domain.repository.EstadoRepository;
import com.estudo.gmfood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		
		if(estado.isPresent()) {
			return ResponseEntity.ok(estado.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
		Estado salvar = estadoRepository.save(estado);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(salvar);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
		Optional<Estado> estadoAtual = estadoRepository.findById(id);
		
		if(estadoAtual.isPresent()) {
			estadoAtual.get().setNome(estado.getNome());
			
			Estado estadoSalva = cadastroEstadoService.salvar(estadoAtual.get());
			return ResponseEntity.ok(estadoSalva);
			
		} 
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Estado> remover(@PathVariable Long id) {
		try {
			cadastroEstadoService.excluir(id);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		
		}catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}
