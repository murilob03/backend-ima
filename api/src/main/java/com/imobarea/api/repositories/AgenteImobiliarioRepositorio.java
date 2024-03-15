package com.imobarea.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.imobarea.api.models.Cliente;

interface AgenteImobiliarioRepositorio extends CrudRepository<Cliente, Long> {
    
}
