package com.imobarea.api.models.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;

public record CriarConsultoriaDTO(
        @NotBlank(message = "CPF do cliente não pode ser vazio") String cpfCliente,

        @NotBlank(message = "CRECI do agente não pode ser vazio") String creciAgente,

        @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date dataHora) {
}
