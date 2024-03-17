package com.imobarea.api.models.dto;

public record LerAgenteDTO(
        LerUsuarioDTO usuario,
        String creci,
        String cpf,
        String cnpjImobiliaria) {

}
