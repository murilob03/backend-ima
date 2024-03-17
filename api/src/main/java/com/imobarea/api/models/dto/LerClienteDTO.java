package com.imobarea.api.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = {"usuario"})
public record LerClienteDTO (
        LerUsuarioDTO usuario,
        String cpf
) {
    public String getNome() {
        return usuario.nome();
    }

    public String getTelefone() {
        return usuario.telefone();
    }

    public String getEmail() {
        return usuario.email();
    }
}
