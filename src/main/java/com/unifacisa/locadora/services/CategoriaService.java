package com.unifacisa.locadora.services;

import com.unifacisa.locadora.entities.Categoria;
import com.unifacisa.locadora.entities.Filme;
import com.unifacisa.locadora.exceptions.ResourceNotFoundException;
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

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    FilmeRepository filmeRepository;


    @Transactional
    @CachePut(value = "categoriasCache", key = "#result.id")
    public Categoria adicionaCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }


    @Transactional
    @Cacheable(value = "categoriasCache")
    public List<Categoria> retornaTodasAsCategorias(){
        return categoriaRepository.findAll();

    }


    @Transactional
    public Set<Filme> retornaFilmesPorCategoria(Long id){
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Categoria não encontrada"));
        return categoria.getFilmes();
    }


    @Transactional
    @CachePut(value = "categoriasCache", key = "#id")
    public Categoria update(Long id, Categoria categoriaUpdated) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Categoria não encontrada"));

        categoria.setNome(categoriaUpdated.getNome());

        return categoriaRepository.save(categoria);
    }


    @Transactional
    @CacheEvict(value = "categoriasCache", key = "#id")
    public void deletaCategoria(long id){
        Categoria categoria  = categoriaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        for(Filme filme : categoria.getFilmes()){
            filme.getCategorias().remove(categoria);
            filmeRepository.save(filme);
        }

        categoriaRepository.delete(categoria);
    }

}
