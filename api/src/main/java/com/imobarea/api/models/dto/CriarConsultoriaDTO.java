package com.imobarea.api.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CriarConsultoriaDTO {
    @NotBlank(message = "CRECI do agente não pode ser vazio")
    private String creciAgente;

    @NotBlank
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
    private String data;

    @NotBlank
    @Pattern(regexp = "\\d{2}:\\d{2}")
    private String hora;
}
