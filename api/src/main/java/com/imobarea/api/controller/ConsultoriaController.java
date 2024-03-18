package com.imobarea.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imobarea.api.models.dto.AtualizarConsultoriaDTO;
import com.imobarea.api.models.dto.CriarConsultoriaDTO;
import com.imobarea.api.models.entity.AgenteImobiliario;
import com.imobarea.api.models.entity.Cliente;
import com.imobarea.api.models.entity.Consultoria;
import com.imobarea.api.repositories.AgenteRepositorio;
import com.imobarea.api.repositories.ClienteRepositorio;
import com.imobarea.api.repositories.ConsultoriaRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

import lombok.NonNull;

@RestController
@RequestMapping("consultoria")
public class ConsultoriaController {

    @Autowired
    private ConsultoriaRepositorio consultoriaRepo;

    @Autowired
    private AgenteRepositorio agenteRepo;

    @Autowired
    private ClienteRepositorio clienteRepo;

    @SuppressWarnings("null")
    @Operation(summary = "Cria uma nova consultoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consultoria criada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CriarConsultoriaDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Erro ao criar consultoria", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CriarConsultoriaDTO criarConsultoria(@RequestBody @Valid @NonNull CriarConsultoriaDTO consultoriaDTO) {
        AgenteImobiliario agente = agenteRepo.findById(consultoriaDTO.getCreciAgente()).orElse(null);
        if (agente == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agente imobiliário não encontrado");

        Cliente cliente = clienteRepo.findById(consultoriaDTO.getCpfCliente()).orElse(null);
        if (cliente == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");

        Consultoria consultoria = new Consultoria(cliente, agente, consultoriaDTO.getData(), consultoriaDTO.getHora());

        try {
            consultoria = consultoriaRepo.save(consultoria);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar cliente");
        }

        return consultoriaDTO;
    }

    @Operation(summary = "Lista todas as consultorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultorias listadas com sucesso", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CriarConsultoriaDTO.class))) }),
            @ApiResponse(responseCode = "400", description = "Erro ao listar consultorias", content = @Content)
    })
    @GetMapping("/todos")
    public Iterable<Consultoria> listarConsultorias() {
        return consultoriaRepo.findAll();
    }

    @Operation(summary = "Busca consultorias associadas ao usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultorias listadas com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Consultoria.class)) }),
            @ApiResponse(responseCode = "500", description = "Erro ao listar consultorias", content = @Content)
    })
    @GetMapping
    public Iterable<Consultoria> listarConsultoriasUsuario() {
        return consultoriaRepo.findAll();
    }

    @Operation(summary = "Deleta uma consultoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consultoria deletada com sucesso", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar consultoria", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarConsultoria(@NonNull Long id) {
        try {
            consultoriaRepo.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao deletar consultoria");
        }
    }

    @Operation(summary = "Atualiza uma consultoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultoria atualizada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CriarConsultoriaDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Consulta ou agente imobiliário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar consultoria", content = @Content)
    })
    @PatchMapping("/{id}")
    public CriarConsultoriaDTO atualizarConsultoria(@NonNull Long id,
            @RequestBody @Valid @NonNull AtualizarConsultoriaDTO consultoriaDTO) {
        Consultoria consultoriaAtualizada = consultoriaRepo.findById(id).orElse(null);

        if (consultoriaAtualizada != null) {
            if (consultoriaDTO.getCreciAgente() != null) {
                @SuppressWarnings("null")
                AgenteImobiliario agente = agenteRepo.findById(consultoriaDTO.getCreciAgente()).orElse(null);
                if (agente != null)
                    consultoriaAtualizada.setAgenteImobiliario(agente);
                else
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agente imobiliário não encontrado");
            }
            if (consultoriaDTO.getData() != null)
                consultoriaAtualizada.setData(consultoriaDTO.getData());
            if (consultoriaDTO.getHora() != null)
                consultoriaAtualizada.setData(consultoriaDTO.getHora());
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Consultoria não encontrada");

        consultoriaRepo.save(consultoriaAtualizada);

        CriarConsultoriaDTO consultoriaSalva = new CriarConsultoriaDTO(
                consultoriaAtualizada.getCliente().getCpf(),
                consultoriaAtualizada.getAgenteImobiliario().getCreci(),
                consultoriaAtualizada.getData(),
                consultoriaAtualizada.getHora());

        return consultoriaSalva;
    }
}
