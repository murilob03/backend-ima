package com.imobarea.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imobarea.api.models.dto.AtualizarUsuarioDTO;
import com.imobarea.api.models.dto.LerClienteDTO;
import com.imobarea.api.models.entity.Cliente;
import com.imobarea.api.models.entity.UserRole;
import com.imobarea.api.repositories.ClienteRepositorio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Operation(summary = "Busca um cliente pelo CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LerClienteDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content) })
    @GetMapping("/{cpf}")
    @SecurityRequirement(name = "bearerAuth")
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
    @SecurityRequirement(name = "bearerAuth")
    public List<LerClienteDTO> buscarTodosClientes() {
        List<Cliente> clientes = (List<Cliente>) clienteRepositorio.findAll();
        List<LerClienteDTO> clientesDTO = new ArrayList<LerClienteDTO>();

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
    @SecurityRequirement(name = "bearerAuth")
    public Cliente atualizarCliente(@PathVariable @NonNull @NotBlank String cpf,
            @RequestBody @Valid AtualizarUsuarioDTO usuarioDTO) {

        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!clienteLogado.getCpf().equals(cpf) && !clienteLogado.getRole().equals(UserRole.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você não tem permissão para atualizar este cliente");

        Cliente clienteAtualizado = clienteRepositorio.findById(cpf).orElse(null);

        if (clienteAtualizado != null) {
            if (usuarioDTO.getTelefone() != null)
                clienteAtualizado.setTelefone(usuarioDTO.getTelefone());
            if (usuarioDTO.getEmail() != null)
                clienteAtualizado.setEmail(usuarioDTO.getEmail());
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");

        return clienteRepositorio.save(clienteAtualizado);
    }

    @Operation(summary = "Deleta um cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content) })
    @DeleteMapping("/{cpf}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deletarCliente(@PathVariable @NonNull @NotBlank String cpf) {
        Cliente clienteLogado = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!clienteLogado.getCpf().equals(cpf) && !clienteLogado.getRole().equals(UserRole.ADMIN))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Você não tem permissão para deletar este cliente");

        if (clienteRepositorio.existsById(cpf)) {
            clienteRepositorio.deleteById(cpf);
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
        }
    }

    private LerClienteDTO mapEntityToDTO(Cliente cliente) {

        return new LerClienteDTO(cliente.getNome(), cliente.getTelefone(), cliente.getEmail(), cliente.getCpf());
    }
}