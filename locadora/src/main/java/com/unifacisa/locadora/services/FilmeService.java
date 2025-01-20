package com.unifacisa.locadora.services;

import com.unifacisa.locadora.exceptions.ResourceNotFoundException;
import com.unifacisa.locadora.model.dtos.FilmeIdTituloCapaDTO;
import com.unifacisa.locadora.model.dtos.FilmeDTO;
import com.unifacisa.locadora.model.entities.Categoria;
import com.unifacisa.locadora.model.entities.Filme;
import com.unifacisa.locadora.repositories.CategoriaRepository;
import com.unifacisa.locadora.repositories.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;


    @Transactional
    public Filme insert(Filme filme) {
        return filmeRepository.save(filme);
    }


    @Transactional(readOnly = true)
    public Page<FilmeIdTituloCapaDTO> getFilmeTituloCapaDTOPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return filmeRepository.findAllFilmeTituloCapaDTOs(pageable);
    }


    @Cacheable(value = "filmesCache")
    @Transactional
    public FilmeDTO findById(Long id) {
        Optional<FilmeDTO> filme = filmeRepository.findFilmeDTOById(id);
        return filme.orElse(null);
    }


    @Transactional
    public List<Categoria> getCategoriasDeUmFilme(Long idFilme){
        return filmeRepository.findCategoriasByFilmeId(idFilme);
    }


    @Transactional
    @CachePut(value = "filmesCache", key = "#id")
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

        return filmeRepository.save(filme);
    }


    @Transactional
    @CacheEvict(value = "filmeCache", key = "#id")
    public void delete(Long id) {
        filmeRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public Page<FilmeIdTituloCapaDTO> retornaFilmesTituloCapaDTOPorCategoriaPaginados(Long id, int page, int size) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        Pageable pageable = PageRequest.of(page, size);


        return filmeRepository.findFilmeTituloCapaDTOsByCategoria(categoria.getId(), pageable);
    }


}