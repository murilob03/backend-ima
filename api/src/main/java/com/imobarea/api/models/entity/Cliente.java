package com.imobarea.api.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Cliente extends Usuario {

    @Id
    @NotBlank(message = "CPF não pode ser vazio")
    private String cpf;

    public Cliente(String nome, String telefone, String senha, String email, UserRole role, String cpf) {
        super(nome, telefone, senha, email, role);

        // Normaliza o CPF
        this.cpf = cpf.replaceAll("[^0-9]", "");
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
