package com.imobarea.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.imobarea.api.models.Cliente;

public interface ImobiliariaRepostorio extends CrudRepository<Cliente, Long> {
    
}