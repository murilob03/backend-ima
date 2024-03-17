package com.imobarea.api.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Cliente extends Usuario {

    @Id
    @NotBlank(message = "CPF não pode ser vazio")
    private String cpf;

    public String getCpf() {
        return cpf;
    }

}
