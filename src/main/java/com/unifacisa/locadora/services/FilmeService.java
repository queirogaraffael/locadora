package com.unifacisa.locadora.services;

import com.unifacisa.locadora.entities.Filme;
import com.unifacisa.locadora.repositories.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

    @Autowired
    FilmeRepository filmeRepository;


    @Transactional
    @CacheEvict(value = {"filmeCache", "filmeCache"}, allEntries = true)
    public Filme insert(Filme filme) {
        return filmeRepository.save(filme);
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "filmesCache")
    public List<Filme> findAll() {
        return filmeRepository.findAll();
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "filmeCache", key = "#id")
    public Optional<Filme> findById(Long id) {
        return filmeRepository.findById(id);
    }


    @Transactional
    @CacheEvict(value = {"filmesCache", "filmeCache"}, key = "#id")
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


    @Transactional
    @CacheEvict(value = {"filmesCache", "filmeCache"}, key = "#id")
    public void delete(Long id) {
        filmeRepository.deleteById(id);
    }

}
