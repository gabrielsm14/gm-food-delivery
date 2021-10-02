package com.estudo.gmfood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudo.gmfood.domain.exception.EntidadeNaoEncontradaException;
import com.estudo.gmfood.domain.model.Cidade;
import com.estudo.gmfood.domain.repository.CidadeRepository;
import com.estudo.gmfood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRespository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@GetMapping
	public List<Cidade> listar() {
		return cidadeRespository.findAll();
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
		Optional<Cidade> cidade = cidadeRespository.findById(id);
		
		if(cidade.isPresent()) {
			return ResponseEntity.ok(cidade.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			cidade = cadastroCidadeService.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
		try {
			Optional<Cidade> cidadeAtual = cidadeRespository.findById(id);
			
			if(cidadeAtual.isPresent()) {
				BeanUtils.copyProperties(cidade, cidadeAtual.get(), "id");
				
				Cidade cidadeSalva = cidadeRespository.save(cidadeAtual.get());
				return ResponseEntity.ok(cidadeSalva);
			}
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
}






