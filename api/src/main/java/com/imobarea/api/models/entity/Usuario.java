package com.imobarea.api.models.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
public class Usuario implements UserDetails {

    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    private String nome;

    @Pattern(regexp = "(\\(\\d{2}\\)|\\d{2})(\\d{4,5}-?\\d{4}|\\d{8,9})", message = "Telefone inválido")
    @Column(unique = true, nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String senha;

    @Email(message = "Email inválido")
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private UserRole role;

    public Usuario(String nome, String telefone, String senha, String email, UserRole role) {
        this.nome = nome;

        // Normaliza o telefone
        this.telefone = telefone.replaceAll("[^0-9]", "");
        
        this.senha = senha;
        this.email = email;
        this.role = role;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.getSenha();
    }

    @Override
    public String getUsername() {
        return this.getTelefone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
