package com.imobarea.api.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;

public record CriarAgenteDTO(
        @NotBlank String nome,

        @Pattern(regexp = "(\\(\\d{2}\\)|\\d{2})(\\d{4,5}-?\\d{4}|\\d{8,9})", message = "Telefone inválido") String telefone,

        @Email String email,

        @NotBlank String cpf,

        @NonNull @NotBlank(message = "CRECI não pode ser vazio") String creci,

        String cnpjImobiliaria) {

}
