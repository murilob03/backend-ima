package com.imobarea.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.imobarea.api.models.entity.AgenteImobiliario;

public interface AgenteRepositorio extends CrudRepository<AgenteImobiliario, String> {
    
}
