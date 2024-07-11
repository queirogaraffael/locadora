package com.unifacisa.locadora.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.unifacisa.locadora.entities.Filme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{
}
