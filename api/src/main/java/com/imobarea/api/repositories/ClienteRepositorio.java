package com.imobarea.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.imobarea.api.models.Cliente;

public interface ClienteRepositorio extends CrudRepository<Cliente, Long> {
    
}
