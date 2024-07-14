package com.unifacisa.locadora.repositories;

import com.unifacisa.locadora.model.DTOs.FilmeDTO;
import com.unifacisa.locadora.model.entities.Categoria;
import com.unifacisa.locadora.model.entities.Filme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{
    Page<Filme> findByCategorias(Categoria categoria, Pageable pageable);

    @Query("SELECT new com.unifacisa.locadora.model.DTOs.FilmeDTO(filme.id, filme.capaUrl) FROM Filme filme")
    Page<FilmeDTO> findAllFilmeDTOs(Pageable pageable);

    @Query("SELECT new com.unifacisa.locadora.model.DTOs.FilmeDTO(filme.id, filme.capaUrl) " +
            "FROM Filme filme JOIN filme.categorias categoria WHERE categoria.id = :categoriaId")
    Page<FilmeDTO> findFilmeDTOsByCategoria(@Param("categoriaId") String categoriaId, Pageable pageable);

}
