package com.imobarea.api.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AtualizarConsultoriaDTO {
    @NotBlank(message = "CPF do cliente não pode ser vazio")
    private String creciAgente;

    @Pattern(regexp = "\\d{4}/\\d{2}/\\d{2}")
    private String data;

    @Pattern(regexp = "\\d{2}:\\d{2}") 
    private String hora;

}
