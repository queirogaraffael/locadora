package com.unifacisa.locadora.resources;

import java.util.List;

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

import com.unifacisa.locadora.entities.Filme;
import com.unifacisa.locadora.services.FilmeService;

@RestController
@RequestMapping("/filmes")
public class FilmeResource {

	@Autowired
	private FilmeService filmeService;

	@GetMapping
	public ResponseEntity<List<Filme>> listarTodos() {
		List<Filme> filmes = filmeService.findAll();
		return new ResponseEntity<>(filmes, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Filme> obterPorId(@PathVariable Long id) {
		Filme filme = filmeService.findById(id);
		if (filme != null) {
			return new ResponseEntity<>(filme, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<Filme> salvar(@RequestBody Filme filme) {
		Filme novoFilme = filmeService.insert(filme);
		return new ResponseEntity<>(novoFilme, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		filmeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Filme> update(@PathVariable Long id, @RequestBody Filme filme) {
		filme = filmeService.update(id, filme);
		return ResponseEntity.ok().body(filme);
	}
}
