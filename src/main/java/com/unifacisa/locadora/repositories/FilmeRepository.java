package com.unifacisa.locadora.repositories;

import com.unifacisa.locadora.entities.Categoria;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.unifacisa.locadora.entities.Filme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{
    Page<Filme> findByCategorias(Categoria categoria, Pageable pageable);
}
