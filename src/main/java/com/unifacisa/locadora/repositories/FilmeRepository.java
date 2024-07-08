package com.unifacisa.locadora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unifacisa.locadora.entities.Filme;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long>{

}
