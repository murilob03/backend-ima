package com.imobarea.api.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class AgenteImobiliario extends Usuario {

    @Id
    private String creci;

    @NotBlank(message = "CPF não pode ser vazio")
    private String cpf;

    private String cnpjImobiliaria;

    public String getCreci() {
        return creci;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCnpjImobiliaria() {
        return cnpjImobiliaria;
    }

    public void setCnpjImobiliaria(String cnpjImobiliaria) {
        this.cnpjImobiliaria = cnpjImobiliaria;
    }
}
