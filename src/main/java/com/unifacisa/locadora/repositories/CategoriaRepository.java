package com.unifacisa.locadora.repositories;

import com.unifacisa.locadora.entities.Categoria;
import com.unifacisa.locadora.model.projections.CategoriaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<CategoriaProjection> findAllProjectedBy();
}
