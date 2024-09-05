package com.unifacisa.locadora.services;

import com.unifacisa.locadora.exceptions.ResourceNotFoundException;
import com.unifacisa.locadora.model.entities.Categoria;
import com.unifacisa.locadora.model.entities.Filme;
import com.unifacisa.locadora.repositories.CategoriaRepository;
import com.unifacisa.locadora.repositories.FilmeRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FilmeRepository filmeRepository;


    @Transactional
    public Categoria adicionaCategoria(Categoria categoria){
        return categoriaRepository.save(categoria);
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "categoriasCache")
    public List<Categoria> retornaTodasAsCategorias(){
        return categoriaRepository.findAll();

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
