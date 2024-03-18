package com.imobarea.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.imobarea.api.models.entity.Cliente;

public interface ClienteRepositorio extends CrudRepository<Cliente, String> {
    UserDetails findByTelefone(String telefone);
}
