package com.unifacisa.locadora.dtos.filme;

import java.time.LocalDate;

public record FilmeRequestDTO(String titulo,
                              String descricao,
                              LocalDate dataLancamento,
                               String capaUrl) {
}
