package com.imobarea.api.models.dto;

import lombok.Getter;

@Getter
public class LerClienteDTO extends LerUsuarioDTO {
    private String cpf;

    public LerClienteDTO(String nome, String telefone, String email, String cpf) {
        super(nome, telefone, email);
        this.cpf = cpf;
    }
}
