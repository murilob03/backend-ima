package com.imobarea.api.models.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public record AtualizarConsultoriaDTO(
        String creciAgente,

        @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date dataHora) {

}
