package com.imobarea.api.models.dto;

import jakarta.validation.constraints.Pattern;

public record AtualizarConsultoriaDTO(
        String creciAgente,

        @Pattern(regexp = "\\d{4}/\\d{2}/\\d{2}") String data,

        @Pattern(regexp = "\\d{2}:\\d{2}") String hora) {

}
