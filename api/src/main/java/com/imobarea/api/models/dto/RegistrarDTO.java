package com.imobarea.api.models.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegistrarDTO {
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;

    @Pattern(regexp = "(\\(\\d{2}\\)|\\d{2})(\\d{4,5}-?\\d{4}|\\d{8,9})", message = "Telefone inválido")
    private String telefone;
    
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String senha;
    
    @Email(message = "Email inválido")
    private String email;
    
    @NotBlank(message = "CPF não pode ser vazio")
    private String cpf;

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
