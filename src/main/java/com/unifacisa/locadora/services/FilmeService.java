package com.unifacisa.locadora.services;

import com.unifacisa.locadora.entities.Categoria;
import com.unifacisa.locadora.entities.Filme;
import com.unifacisa.locadora.exceptions.ResourceNotFoundException;
import com.unifacisa.locadora.model.DTOs.CategoriaDTO;
import com.unifacisa.locadora.repositories.CategoriaRepository;
import com.unifacisa.locadora.repositories.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

    @Autowired
    FilmeRepository filmeRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    private CacheManager cacheManager;


    @Transactional
    public Filme insert(Filme filme) {
        return filmeRepository.save(filme);
    }


    @Transactional(readOnly = true)
    public List<Filme> findAll() {
        return filmeRepository.findAll();
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "filmeCache", key = "#id")
    public Filme findById(Long id) {
        return filmeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Filme não encontrado"));
    }


    @Transactional
    public Filme update(Long id, Filme filmeUpdated) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filme com este id não encontrado."));

        filme.setNome(filmeUpdated.getNome());
        filme.setDiretor(filmeUpdated.getDiretor());
        filme.setCategorias(filmeUpdated.getCategorias());

        Filme updatedFilme = filmeRepository.save(filme);

        Cache filmeCache = cacheManager.getCache("filmeCache");
        if (filmeCache != null && filmeCache.get(id) != null) {
            filmeCache.put(id, updatedFilme);
        }

        return updatedFilme;
    }


    @Transactional
    @CacheEvict(value = "filmeCache", key = "#id")
    public void delete(Long id) {
        filmeRepository.deleteById(id);
    }

}