package com.imobarea.api.models.dto;

import lombok.Getter;

@Getter
public class LerAgenteDTO extends LerUsuarioDTO {
    private String creci;
    private String cnpjImobiliaria;

    public LerAgenteDTO(String nome, String telefone, String email, String creci, String cnpjImobiliaria) {
        super(nome, telefone, email);
        this.creci = creci;
        this.cnpjImobiliaria = cnpjImobiliaria;
    }
}
