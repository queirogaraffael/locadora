package com.unifacisa.locadora.services;

import com.unifacisa.locadora.entities.Categoria;
import com.unifacisa.locadora.entities.Filme;
import com.unifacisa.locadora.exceptions.ResourceNotFoundException;
import com.unifacisa.locadora.model.DTOs.CategoriaDTO;
import com.unifacisa.locadora.model.projections.CategoriaProjection;
import com.unifacisa.locadora.repositories.CategoriaRepository;
import com.unifacisa.locadora.repositories.FilmeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    FilmeRepository filmeRepository;


    @Transactional
    @CachePut(value = "categoriasDTOCache", key = "#result.id")
    public CategoriaDTO adicionaCategoria(Categoria categoria){

        categoriaRepository.save(categoria);

        return new CategoriaDTO(categoria.getId(), categoria.getNome());
    }


    @Transactional
    @Cacheable(value = "categoriasDTOCache", key = "'all'")
    public List<CategoriaDTO> retornaTodasAsCategoriasDTO(){
        List<CategoriaProjection> categoriaProjection = categoriaRepository.findAllProjectedBy();

        return categoriaProjection.stream()
                .map(projection -> new CategoriaDTO(projection.getId(), projection.getNome()))
                .collect(Collectors.toList());
    }


    @Transactional
    public Set<Filme> retornaFilmesPorCategoria(Long id){
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Categoria não encontrada"));
        return categoria.getFilmes();
    }


    @Transactional
    @CachePut(value = "categoriasDTOCache", key = "#id")
    public CategoriaDTO update(Long id, Categoria categoriaUpdated) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Categoria não encontrada"));

        categoria.setNome(categoriaUpdated.getNome());

        categoriaRepository.save(categoria);

        return new CategoriaDTO(id, categoria.getNome());
    }


    @Transactional
    @CacheEvict(value = "categoriasDTOCache", key = "#id")
    public void deletaCategoria(long id){
        Categoria categoria  = categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        for(Filme filme : categoria.getFilmes()){
            filme.getCategorias().remove(categoria);
            filmeRepository.save(filme);
        }

        categoriaRepository.delete(categoria);
    }

}
