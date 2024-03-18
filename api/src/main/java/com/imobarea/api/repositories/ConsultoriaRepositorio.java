package com.imobarea.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imobarea.api.models.entity.Consultoria;

public interface ConsultoriaRepositorio extends JpaRepository<Consultoria, Long> {
    List<Consultoria> findByClienteCpf(String cpf);
}
