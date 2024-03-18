package com.imobarea.api.models.dto;

import lombok.Getter;

@Getter
public class LoginDTO {
    private String telefone;
    private String senha;

    public LoginDTO(String telefone, String senha) {
        // Normaliza o telefone
        this.telefone = telefone.replaceAll("[^0-9]", "");
        this.senha = senha;
    }
}
