package com.imobarea.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.imobarea.api.models.Cliente;

public interface ConsultoriaRepositorio extends CrudRepository<Cliente, Long> {
    
}
