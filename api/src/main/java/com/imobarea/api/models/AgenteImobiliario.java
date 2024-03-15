package com.imobarea.api.models;

import jakarta.persistence.*;

@Entity
public class AgenteImobiliario extends Cliente{

    private long creci;

    private long cpf;

    private long cnpjImobiliaria;

    public long getCreci() {
        return creci;
    }

    public long getCpf() {
        return cpf;
    }

    public long getCnpjImobiliaria() {
        return cnpjImobiliaria;
    }

    public void setCnpjImobiliaria(long cnpjImobiliaria) {
        this.cnpjImobiliaria = cnpjImobiliaria;
    }
}
