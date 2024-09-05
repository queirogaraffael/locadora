package com.unifacisa.locadora.services;

import com.unifacisa.locadora.exceptions.ResourceNotFoundException;
import com.unifacisa.locadora.model.DTOs.FilmeDTO;
import com.unifacisa.locadora.model.entities.Categoria;
import com.unifacisa.locadora.model.entities.Filme;
import com.unifacisa.locadora.repositories.CategoriaRepository;
import com.unifacisa.locadora.repositories.FilmeRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CacheManager cacheManager;


    @Transactional
    public Filme insert(Filme filme) {
        return filmeRepository.save(filme);
    }


    @Transactional(readOnly = true)
    public Page<FilmeDTO> getFilmesDTOPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return filmeRepository.findAllFilmeDTOs(pageable);
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "filmesCache")
    public Filme findById(Long id) {
        Filme filme = filmeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Filme não encontrado"));
        Hibernate.initialize(filme.getCategorias());
        return filme;
    }


    @Transactional
    public Filme update(Long id, Filme filmeUpdated) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filme com este id não encontrado."));


        filme.setTitulo(filmeUpdated.getTitulo());
        filme.setDescricao(filmeUpdated.getDescricao());
        filme.setDataLancamento(filmeUpdated.getDataLancamento());
        filme.setRating(filmeUpdated.getRating());
        filme.setDuracao(filmeUpdated.getDuracao());
        filme.setCapaUrl(filmeUpdated.getCapaUrl());
        filme.setTrailerUrl(filmeUpdated.getTrailerUrl());
        filme.setVideoUrl(filmeUpdated.getVideoUrl());
        filme.setCategorias(filmeUpdated.getCategorias());

        Filme updatedFilme = filmeRepository.save(filme);

        Cache filmeCache = cacheManager.getCache("filmesCache");
        if (filmeCache != null && filmeCache.get(id) != null) {
            filmeCache.put(id, updatedFilme);
        }

        return updatedFilme;
    }

    @Transactional
    @CacheEvict(value = "filmesCache", key = "#id")
    public void delete(Long id) {
        filmeRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public Page<FilmeDTO> retornaFilmesDTOPorCategoriaPaginados(Long id, int page, int size) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        Pageable pageable = PageRequest.of(page, size);
        return filmeRepository.findFilmeDTOsByCategoria(categoria.getId().toString(), pageable);
    }


}