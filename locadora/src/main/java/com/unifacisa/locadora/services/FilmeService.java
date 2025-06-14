package com.unifacisa.locadora.services;

import com.unifacisa.locadora.dtos.categoria.CategoriaFilmeDTO;
import com.unifacisa.locadora.dtos.categoria.CategoriaResponseDTO;
import com.unifacisa.locadora.dtos.filme.FilmePreviewDTO;
import com.unifacisa.locadora.dtos.filme.FilmeRequestDTO;
import com.unifacisa.locadora.dtos.filme.FilmeResponseDTO;
import com.unifacisa.locadora.dtos.filme.FilmeUpdateDTO;
import com.unifacisa.locadora.exceptions.ResourceNotFoundException;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;


    @Transactional
    public FilmeResponseDTO insert(FilmeRequestDTO dto) {

        Set<Categoria> categoriasFilme = new HashSet<>();
        for (CategoriaFilmeDTO categoriaFilme : dto.categorias()) {

            Categoria categoria = categoriaRepository.findById(categoriaFilme.id()).orElseThrow(() -> new ResourceNotFoundException("Categoria não criada"));

            categoriasFilme.add(categoria);
        }

        Filme filme = new Filme();

        filme.setTitulo(dto.titulo());
        filme.setDescricao(dto.descricao());
        filme.setDataLancamento(dto.dataLancamento());
        filme.setCapaUrl(dto.capaUrl());

        filme.setCategorias(categoriasFilme);

        Filme filmeSaved = filmeRepository.save(filme);

        return new FilmeResponseDTO(filmeSaved.getId(), filmeSaved.getTitulo(), filmeSaved.getDescricao(), filmeSaved.getDataLancamento(), filmeSaved.getCapaUrl());
    }


    @Transactional(readOnly = true)
    public Page<FilmePreviewDTO> getFilmeTituloCapaDTOPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return filmeRepository.findAllFilmeTituloCapaDTOs(pageable);
    }


    @Cacheable(value = "filmesCache")
    @Transactional
    public FilmeResponseDTO findById(Long id) {
        return filmeRepository.findFilmeDTOById(id).orElseThrow(() -> new ResourceNotFoundException("Filme com ID não encontrado"));

    }


    @Transactional
    public List<CategoriaResponseDTO> getCategoriasDeUmFilme(Long idFilme) {
        return filmeRepository.findCategoriasByFilmeId(idFilme);
    }


    @Transactional
    @CachePut(value = "filmesCache", key = "#id")
    public FilmeResponseDTO update(Long id, FilmeUpdateDTO dto) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filme com este id não encontrado."));

        filme.setTitulo(dto.titulo());
        filme.setDescricao(dto.descricao());
        filme.setDataLancamento(dto.dataLancamento());
        filme.setCapaUrl(dto.capaUrl());

        Filme filmeUpdated = filmeRepository.save(filme);

        return new FilmeResponseDTO(
                filmeUpdated.getId(),
                filmeUpdated.getTitulo(),
                filmeUpdated.getDescricao(),
                filmeUpdated.getDataLancamento(),
                filmeUpdated.getCapaUrl()
        );
    }


    @Transactional
    @CacheEvict(value = "filmeCache", key = "#id")
    public void delete(Long id) {
        filmeRepository.deleteById(id);
    }


    @Transactional(readOnly = true)
    public Page<FilmePreviewDTO> retornaFilmesTituloCapaDTOPorCategoriaPaginados(Long id, int page, int size) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        Pageable pageable = PageRequest.of(page, size);


        return filmeRepository.findFilmeTituloCapaDTOsByCategoria(categoria.getId(), pageable);
    }


}