package com.unifacisa.locadora.repositories;

import com.unifacisa.locadora.dtos.categoria.CategoriaResponseDTO;
import com.unifacisa.locadora.dtos.filme.FilmePreviewDTO;
import com.unifacisa.locadora.dtos.filme.FilmeResponseDTO;
import com.unifacisa.locadora.model.entities.Filme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    @Query(value = "SELECT new com.unifacisa.locadora.model.dtos.filme.FilmePreviewDTO(f.id, f.titulo, f.capaUrl) FROM Filme f"
    , countQuery = "SELECT count(f) FROM Filme f")
    Page<FilmePreviewDTO> findAllFilmeTituloCapaDTOs(Pageable pageable);

    @Query(value = "SELECT new com.unifacisa.locadora.model.dtos.filme.FilmePreviewDTO(f.id, f.titulo, f.capaUrl) " +
            "FROM Filme f JOIN f.categorias c WHERE c.id = :categoriaId",
            countQuery = "SELECT count(f) FROM Filme f JOIN f.categorias c WHERE c.id = :categoriaId"
    )
    Page<FilmePreviewDTO> findFilmeTituloCapaDTOsByCategoria(@Param("categoriaId") Long categoriaId, Pageable pageable);

    @Query(value = "SELECT new com.unifacisa.locadora.model.dtos.filme.FilmeResponseDTO(f.id, f.titulo, f.descricao, " +
            "f.dataLancamento, f.capaUrl) " +
    "FROM Filme f WHERE f.id = :filmeId")
    Optional<FilmeResponseDTO> findFilmeDTOById(@Param("filmeId") Long idFilme);

    @Query(value = "SELECT new com.unifacisa.locadora.model.dtos.CategoriaResponseDTO(c.id, c.nome) " +
            "FROM Filme f JOIN f.categorias c WHERE f.id = :filmeId",
            countQuery = "SELECT count(c) FROM Filme f JOIN f.categorias c WHERE f.id = :filmeId")
    Page<CategoriaResponseDTO> findCategoriasByFilmeId(@Param("filmeId") Long filmeId, Pageable pageable);

}

