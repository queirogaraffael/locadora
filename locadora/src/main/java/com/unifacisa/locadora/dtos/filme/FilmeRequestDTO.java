package com.unifacisa.locadora.dtos.filme;

import com.unifacisa.locadora.dtos.categoria.CategoriaFilmeDTO;

import java.time.LocalDate;
import java.util.Set;

public record FilmeRequestDTO(String titulo,
                              String descricao,
                              LocalDate dataLancamento,
                              String capaUrl, Set<CategoriaFilmeDTO> categorias) {
}
