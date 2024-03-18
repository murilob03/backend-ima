package com.imobarea.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imobarea.api.models.dto.LerAgenteDTO;
import com.imobarea.api.models.entity.AgenteImobiliario;
import com.imobarea.api.repositories.AgenteRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.Valid;
import lombok.NonNull;

@RestController
@RequestMapping("/agente")
public class AgenteController {

    @Autowired
    private AgenteRepositorio agenteRepositorio;

    @Operation(summary = "Cria um novo agente imobiliário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agente criado com sucesso.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AgenteImobiliario.class)) }),
            @ApiResponse(responseCode = "400", description = "Erro ao criar agente.", content = @Content)
    })
    @PostMapping("/criar")
    public AgenteImobiliario criarAgente(@RequestBody @NonNull @Valid AgenteImobiliario agente) {
        try {
            agente = agenteRepositorio.save(agente);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar agente.");
        }

        return agente;
    }

    @Operation(summary = "Busca todos os agentes imobiliários cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LerAgenteDTO.class))) }),
    })
    @GetMapping("/todos")
    public Iterable<LerAgenteDTO> listarTodos() {
        Iterable<AgenteImobiliario> agentes = agenteRepositorio.findAll();
        List<LerAgenteDTO> agentesDTO = new ArrayList<LerAgenteDTO>();

        for (AgenteImobiliario agente : agentes) {
            agentesDTO.add(entityToDTO(agente));
        }

        return agentesDTO;
    }

    private LerAgenteDTO entityToDTO(AgenteImobiliario agente) {
        return new LerAgenteDTO(agente.getNome(), agente.getTelefone(), agente.getEmail(), agente.getCreci(),
                agente.getCnpjImobiliaria());
    }
}
