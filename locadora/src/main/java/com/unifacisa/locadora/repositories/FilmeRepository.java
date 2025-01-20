package com.unifacisa.locadora.repositories;

import com.unifacisa.locadora.model.dtos.FilmeDTO;
import com.unifacisa.locadora.model.dtos.FilmeIdTituloCapaDTO;
import com.unifacisa.locadora.model.entities.Categoria;
import com.unifacisa.locadora.model.entities.Filme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    @Query("SELECT new com.unifacisa.locadora.model.dtos.FilmeIdTituloCapaDTO(f.id, f.titulo, f.capaUrl) FROM Filme f")
    Page<FilmeIdTituloCapaDTO> findAllFilmeTituloCapaDTOs(Pageable pageable);


    @Query("SELECT new com.unifacisa.locadora.model.dtos.FilmeIdTituloCapaDTO(f.id, f.titulo, f.capaUrl) " +
            "FROM Filme f JOIN f.categorias c WHERE c.id = :categoriaId")
    Page<FilmeIdTituloCapaDTO> findFilmeTituloCapaDTOsByCategoria(@Param("categoriaId") Long categoriaId, Pageable pageable);


    @Query("SELECT new com.unifacisa.locadora.model.dtos.FilmeDTO(f.id, f.titulo, f.descricao, " +
            "f.dataLancamento, f.rating, f.duracao, f.capaUrl, f.trailerUrl, f.videoUrl) " +
    "FROM Filme f WHERE f.id = :filmeId")
    Optional<FilmeDTO> findFilmeDTOById(@Param("filmeId") Long idFilme);


    @Query("SELECT f.categorias FROM Filme f WHERE f.id = :filmeId")
    List<Categoria> findCategoriasByFilmeId(@Param("filmeId") Long filmeId);
}

