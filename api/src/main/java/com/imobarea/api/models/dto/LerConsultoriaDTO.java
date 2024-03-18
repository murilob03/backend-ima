package com.imobarea.api.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LerConsultoriaDTO extends CriarConsultoriaDTO{
    @NotBlank(message = "CPF do cliente não pode ser vazio")
    private String cpfCliente;

    public LerConsultoriaDTO(String creciAgente, String data, String hora, String cpfCliente) {
        super(creciAgente, data, hora);
        this.cpfCliente = cpfCliente;
    }
}
