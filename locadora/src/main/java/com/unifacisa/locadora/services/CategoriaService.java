package com.unifacisa.locadora.services;

import com.unifacisa.locadora.dtos.categoria.CategoriaRequestDTO;
import com.unifacisa.locadora.dtos.categoria.CategoriaResponseDTO;
import com.unifacisa.locadora.dtos.categoria.CategoriaUpdateDTO;
import com.unifacisa.locadora.exceptions.ResourceNotFoundException;
import com.unifacisa.locadora.model.entities.Categoria;
import com.unifacisa.locadora.model.entities.Filme;
import com.unifacisa.locadora.repositories.CategoriaRepository;
import com.unifacisa.locadora.repositories.FilmeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public CategoriaResponseDTO adicionaCategoria(CategoriaRequestDTO dto){

        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());

        Categoria categoriaSaved = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(categoria.getId(), categoriaSaved.getNome());
    }


    @Transactional(readOnly = true)
    public Page<CategoriaResponseDTO> retornaTodasAsCategoriasPaginadas(Pageable pageable){
        return categoriaRepository.retornaCategoriasPaginada(pageable);

    }


    @Transactional
    public CategoriaResponseDTO update(Long id, CategoriaUpdateDTO dto) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Categoria não encontrada"));

        categoria.setNome(dto.nome());

        Categoria categoriaSaved = categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(categoria.getId(), categoriaSaved.getNome());
    }

}
