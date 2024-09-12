package com.unifacisa.locadora.model.dtos;

public record FilmeDTO(
        Long id,
        String titulo,
        String descricao,
        String dataLancamento,
        double rating,
        String duracao,
        String capaUrl,
        String trailerUrl,
        String videoUrl
) {
}
