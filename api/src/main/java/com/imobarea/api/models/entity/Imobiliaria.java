package com.imobarea.api.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Imobiliaria extends Usuario {

    @Id
    @NotBlank(message = "CNPJ não pode ser vazio")
    private String cnpj;

    @Size(min = 3, max = 100, message = "Nome fantasia deve ter entre 3 e 100 caracteres")
    private String nomeFantasia;

    public String getCnpj() {
        return cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }
}
