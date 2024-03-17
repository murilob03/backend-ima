package com.imobarea.api.repositories;

import org.springframework.data.repository.CrudRepository;

import com.imobarea.api.models.entity.Imobiliaria;

public interface ImobiliariaRepostorio extends CrudRepository<Imobiliaria, String> {
    
}
