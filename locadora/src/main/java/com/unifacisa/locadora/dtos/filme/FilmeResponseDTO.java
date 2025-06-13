package com.unifacisa.locadora.dtos.filme;

import java.time.LocalDate;

public record FilmeResponseDTO(Long id,
                               String titulo,
                               String descricao,
                               LocalDate dataLancamento,
                               String capaUrl, Long idCategoria) {
}
