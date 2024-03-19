package com.imobarea.api.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class AgenteImobiliario extends Usuario {

    @Id
    private String creci;

    @NotBlank(message = "CPF não pode ser vazio")
    private String cpf;

    private String cnpjImobiliaria;

    public AgenteImobiliario(String nome, String telefone, String senha, String email, UserRole role, String creci, String cpf, String cnpjImobiliaria) {
        super(nome, telefone, senha, email, role);

        // Normaliza o CRECI, CPF e CNPJ
        this.creci = creci.replaceAll("[^0-9]", "");
        this.cpf = cpf.replaceAll("[^0-9]", "");
        this.cnpjImobiliaria = cnpjImobiliaria.replaceAll("[^0-9]", "");

        this.creci = creci;
        this.cpf = cpf;
        this.cnpjImobiliaria = cnpjImobiliaria;
    }

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
