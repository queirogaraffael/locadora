package com.unifacisa.locadora.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.unifacisa.locadora.entities.Filme;
import com.unifacisa.locadora.repositories.FilmeRepository;
import com.unifacisa.locadora.services.exceptions.DatabaseException;
import com.unifacisa.locadora.services.exceptions.ResourceNotFoundException;

@Service
public class FilmeService {

	@Autowired
	FilmeRepository filmeRepository;

	public Filme insert(Filme filme) {
		return filmeRepository.save(filme);
	}

	public List<Filme> findAll() {
		return filmeRepository.findAll();
	}

	public Filme findById(Long id) {
		Optional<Filme> filme = filmeRepository.findById(id);
		return filme.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Filme update(Long id, Filme filmeUpdated) {
		Optional<Filme> optionalFilme = filmeRepository.findById(id);
		if (optionalFilme.isPresent()) {
			Filme filme = optionalFilme.get();
			filme.setNome(filmeUpdated.getNome());
			filme.setDiretor(filmeUpdated.getDiretor());
			filme.setCategoria(filmeUpdated.getCategoria());
			return filmeRepository.save(filme);
		} else {
			return null;
		}
	}

	public void delete(Long id) {
		try {
			filmeRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}

	}

}
