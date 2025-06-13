package com.unifacisa.locadora.repositories;

import com.unifacisa.locadora.dtos.categoria.CategoriaResponseDTO;
import com.unifacisa.locadora.model.entities.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(value = "SELECT new com.unifacisa.locadora.dtos.categoria.CategoriaResponseDTO(c.id, c.nome) " +
            "FROM Categoria c",
            countQuery = "SELECT count(c) FROM Categoria c")
    Page<CategoriaResponseDTO> retornaCategoriasPaginada(Pageable pageable);

}
