package com.imobarea.api.models;

import jakarta.persistence.*;

@Entity
public class Cliente extends Usuario {

    @Id
    private long cpf;

    public long getCpf() {
        return cpf;
    }
}
