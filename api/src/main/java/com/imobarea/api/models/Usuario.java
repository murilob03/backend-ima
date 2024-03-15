package com.imobarea.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;;

@MappedSuperclass
public class Usuario {

    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Size(min = 10, max = 100, message = "Endereço deve ter entre 10 e 100 caracteres")
    private String endereco;

    private String telefone;

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
