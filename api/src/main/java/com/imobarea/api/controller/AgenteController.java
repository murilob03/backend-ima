package com.imobarea.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imobarea.api.models.dto.CriarAgenteDTO;
import com.imobarea.api.models.dto.LerAgenteDTO;
import com.imobarea.api.models.entity.AgenteImobiliario;
import com.imobarea.api.models.entity.UserRole;
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

    @SuppressWarnings("null")
    @Operation(summary = "Cria um novo agente imobiliário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agente criado com sucesso.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CriarAgenteDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "CRECI já cadastrado.", content = @Content)
    })
    @PostMapping("/criar")
    public ResponseEntity<CriarAgenteDTO> criarAgente(@RequestBody @NonNull @Valid CriarAgenteDTO agenteDTO) {
        if (agenteRepositorio.findById(agenteDTO.creci()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CRECI já cadastrado.");
        }
        
        AgenteImobiliario novoAgente = new AgenteImobiliario(agenteDTO.nome(), agenteDTO.telefone(),
                "senha", agenteDTO.email(), UserRole.USER, agenteDTO.creci(), agenteDTO.cpf(), agenteDTO.cnpjImobiliaria());

        try {
            novoAgente = agenteRepositorio.save(novoAgente);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar agente.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(agenteDTO);
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
