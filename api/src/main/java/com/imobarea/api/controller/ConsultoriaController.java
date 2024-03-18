package com.imobarea.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imobarea.api.models.dto.CriarConsultoriaDTO;
import com.imobarea.api.models.dto.LerConsultoriaDTO;
import com.imobarea.api.models.entity.AgenteImobiliario;
import com.imobarea.api.models.entity.Cliente;
import com.imobarea.api.models.entity.Consultoria;
import com.imobarea.api.repositories.AgenteRepositorio;
import com.imobarea.api.repositories.ConsultoriaRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import lombok.NonNull;

@RestController
@RequestMapping("consultoria")
public class ConsultoriaController {

    @Autowired
    private ConsultoriaRepositorio consultoriaRepo;

    @Autowired
    private AgenteRepositorio agenteRepo;

    @SuppressWarnings("null")
    @Operation(summary = "Cria uma nova consultoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Consultoria criada com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LerConsultoriaDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Erro ao criar consultoria", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public LerConsultoriaDTO criarConsultoria(@RequestBody @Valid @NonNull CriarConsultoriaDTO consultoriaDTO) {
        AgenteImobiliario agente = agenteRepo.findById(consultoriaDTO.getCreciAgente()).orElse(null);
        if (agente == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agente imobiliário não encontrado");

        Cliente cliente = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Consultoria consultoria = new Consultoria(cliente, agente, consultoriaDTO.getData(), consultoriaDTO.getHora());

        try {
            consultoria = consultoriaRepo.save(consultoria);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar cliente");
        }

        LerConsultoriaDTO consultoriaSalva = new LerConsultoriaDTO(
                consultoria.getCliente().getCpf(),
                consultoria.getAgenteImobiliario().getCreci(),
                consultoria.getData(),
                consultoria.getHora());

        return consultoriaSalva;
    }

    @Operation(summary = "Lista todas as consultorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultorias listadas com sucesso", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LerConsultoriaDTO.class))) }),
            @ApiResponse(responseCode = "400", description = "Erro ao listar consultorias", content = @Content)
    })
    @GetMapping("/todos")
    @SecurityRequirement(name = "bearerAuth")
    public Iterable<LerConsultoriaDTO> listarConsultorias() {
        List<Consultoria> consultorias = consultoriaRepo.findAll();

        return consultorias.stream().map(consultoria -> new LerConsultoriaDTO(consultoria.getCliente().getCpf(),
                consultoria.getAgenteImobiliario().getCreci(), consultoria.getData(), consultoria.getHora()))
                .toList();
    }

    @Operation(summary = "Busca consultorias associadas ao usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consultorias listadas com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LerConsultoriaDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Erro ao listar consultorias", content = @Content)
    })
    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    public Iterable<LerConsultoriaDTO> listarConsultoriasUsuario() {
        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Consultoria> consultorias = consultoriaRepo.findByClienteCpf(clienteLogado.getCpf());

        return consultorias.stream().map(consultoria -> new LerConsultoriaDTO(consultoria.getCliente().getCpf(),
                consultoria.getAgenteImobiliario().getCreci(), consultoria.getData(), consultoria.getHora()))
                .toList();
    }

    @Operation(summary = "Deleta uma consultoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Consultoria deletada com sucesso", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro ao deletar consultoria", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
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
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LerConsultoriaDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Consulta ou agente imobiliário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar consultoria", content = @Content)
    })
    @PatchMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public LerConsultoriaDTO atualizarConsultoria(@NonNull Long id,
            @RequestBody @Valid @NonNull CriarConsultoriaDTO consultoriaDTO) {

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

        LerConsultoriaDTO consultoriaSalva = new LerConsultoriaDTO(
                consultoriaAtualizada.getCliente().getCpf(),
                consultoriaAtualizada.getAgenteImobiliario().getCreci(),
                consultoriaAtualizada.getData(),
                consultoriaAtualizada.getHora());

        return consultoriaSalva;
    }
}
