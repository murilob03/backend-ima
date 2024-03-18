package com.imobarea.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.imobarea.api.models.entity.AgenteImobiliario;

public interface AgenteRepositorio extends JpaRepository<AgenteImobiliario, String> {
    UserDetails findByTelefone(String telefone);
}
