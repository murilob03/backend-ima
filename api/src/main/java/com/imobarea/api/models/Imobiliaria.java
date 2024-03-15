package com.imobarea.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
public class Imobiliaria extends Usuario {

    @Id
    private long cnpj;

    @Size(min = 3, max = 100, message = "Nome fantasia deve ter entre 3 e 100 caracteres")
    private String nomeFantasia;

    public long getCnpj() {
        return cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }
}
