package com.imobarea.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imobarea.api.models.dto.AtualizarUsuarioDTO;
import com.imobarea.api.models.dto.LerClienteDTO;
import com.imobarea.api.models.dto.LerUsuarioDTO;
import com.imobarea.api.models.entity.Cliente;
import com.imobarea.api.repositories.ClienteRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.NonNull;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Operation(summary = "Cria um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "400", description = "Erro ao criar cliente", content = @Content)
    })
    @PostMapping
    public Cliente criarCliente(@RequestBody @NonNull @Valid Cliente cliente) {
        try {
            cliente = clienteRepositorio.save(cliente);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar cliente");
        }
        return cliente;
    }

    @Operation(summary = "Busca um cliente pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LerClienteDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content) })
    @GetMapping("/{cpf}")
    public LerClienteDTO buscarCliente(@PathVariable @NonNull @NotBlank String cpf) {
        Cliente cliente = clienteRepositorio.findById(cpf).orElse(null);

        if (cliente != null) {
            return mapEntityToDTO(cliente);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
    }

    @Operation(summary = "Busca todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LerClienteDTO.class))) }),
    })
    @GetMapping("/todos")
    public List<LerClienteDTO> buscarTodosClientes() {
        List<LerClienteDTO> clientesDTO = new ArrayList<LerClienteDTO>();
        List<Cliente> clientes = (List<Cliente>) clienteRepositorio.findAll();

        for (Cliente cliente : clientes) {
            clientesDTO.add(mapEntityToDTO(cliente));
        }

        return clientesDTO;
    }

    @Operation(summary = "Atualiza um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class)) }),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content) })
    @PatchMapping("/{cpf}")
    public Cliente atualizarCliente(@PathVariable @NonNull @NotBlank String cpf,
            @RequestBody AtualizarUsuarioDTO usuarioDTO) {
        Cliente clienteAtualizado = clienteRepositorio.findById(cpf).orElse(null);

        if (clienteAtualizado != null) {
            if (usuarioDTO.telefone() != null)
                clienteAtualizado.setTelefone(usuarioDTO.telefone());
            if (usuarioDTO.endereco() != null)
                clienteAtualizado.setEndereco(usuarioDTO.endereco());
            if (usuarioDTO.email() != null)
                clienteAtualizado.setEmail(usuarioDTO.email());
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");

        return clienteRepositorio.save(clienteAtualizado);
    }

    @Operation(summary = "Deleta um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content) })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Void> deletarCliente(@PathVariable @NonNull @NotBlank String cpf) {
        if (clienteRepositorio.existsById(cpf)) {
            clienteRepositorio.deleteById(cpf);
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
    }

    private LerClienteDTO mapEntityToDTO(Cliente cliente) {
        LerUsuarioDTO usuarioDTO = new LerUsuarioDTO(
                cliente.getNome(),
                cliente.getEndereco(),
                cliente.getTelefone(),
                cliente.getEmail());

        return new LerClienteDTO(usuarioDTO, cliente.getCpf());
    }
}