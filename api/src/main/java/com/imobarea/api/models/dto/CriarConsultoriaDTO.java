package com.imobarea.api.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CriarConsultoriaDTO(
        @NotBlank(message = "CPF do cliente não pode ser vazio") String cpfCliente,

        @NotBlank(message = "CRECI do agente não pode ser vazio") String creciAgente,

        @NotBlank @Pattern(regexp = "\\d{4}/\\d{2}/\\d{2}") String data,

        @NotBlank @Pattern(regexp = "\\d{2}:\\d{2}") String hora) {
}
