package com.unifacisa.locadora.dtos.filme;

import java.time.LocalDate;

public record FilmeUpdateDTO(String titulo,
                             String descricao,
                             LocalDate dataLancamento,
                             String capaUrl) {
}
