package com.unifacisa.locadora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unifacisa.locadora.entities.Filme;

public interface FilmeRepository extends JpaRepository<Filme, Long>{

}
