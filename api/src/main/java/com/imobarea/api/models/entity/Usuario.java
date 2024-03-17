package com.imobarea.api.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
public class Usuario {

    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Size(min = 10, max = 100, message = "Endereço deve ter entre 10 e 100 caracteres")
    private String endereco;

    @Column(unique = true)
    @Pattern(regexp = "(\\(\\d{2}\\))|(\\d{2})\\d{4,5}-?\\d{4}", message = "Telefone inválido")
    private String telefone;

    @Size(min = 8, max = 50, message = "Senha deve ter entre 8 e 50 caracteres")
    private String senha;

    @Email(message = "Email inválido")
    private String email;

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
