package com.imobarea.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imobarea.api.models.entity.AgenteImobiliario;
import com.imobarea.api.repositories.AgenteRepositorio;

import jakarta.validation.Valid;
import lombok.NonNull;

@RestController
@RequestMapping("/agente")
public class AgenteController {

    @Autowired
    private AgenteRepositorio agenteRepositorio;

    @PostMapping("/criar")
    public AgenteImobiliario criarAgente(@RequestBody @NonNull @Valid AgenteImobiliario agente) {
        try {
            agente = agenteRepositorio.save(agente);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar cliente");
        }

        return agente;
    }

}
