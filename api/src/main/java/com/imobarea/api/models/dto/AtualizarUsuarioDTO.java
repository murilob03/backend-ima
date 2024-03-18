package com.imobarea.api.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class AtualizarUsuarioDTO {
    @Pattern(regexp = "(\\(\\d{2}\\)|\\d{2})(\\d{4,5}-?\\d{4}|\\d{8,9})", message = "Telefone inválido")
    private String telefone;
    
    @Email(message = "Email inválido")
    private String email;

}
