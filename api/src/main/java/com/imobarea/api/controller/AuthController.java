package com.imobarea.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imobarea.api.models.dto.RegistrarDTO;
import com.imobarea.api.models.dto.TokenDTO;
import com.imobarea.api.models.dto.LerClienteDTO;
import com.imobarea.api.models.dto.LoginDTO;
import com.imobarea.api.models.entity.Cliente;
import com.imobarea.api.models.entity.UserRole;
import com.imobarea.api.repositories.ClienteRepositorio;
import com.imobarea.api.services.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.NonNull;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private ClienteRepositorio clienteRepo;

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Faz login na aplicação")
    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Usuário ou senha inválidos", content = @Content) })
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO authDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.getTelefone(), authDTO.getSenha());

        Authentication auth;

        try {
            auth = this.authManager.authenticate(usernamePassword);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos");
            } else
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao autenticar usuário");
        }

        TokenDTO tokenDTO = new TokenDTO(this.tokenService.generateToken((Cliente) auth.getPrincipal()));

        return ResponseEntity.ok(tokenDTO);
    }

    @Operation(summary = "Registra um novo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente registrado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RegistrarDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Erro ao registrar cliente", content = @Content) })
    @PostMapping("/registrar")
    public ResponseEntity<RegistrarDTO> registrar(@RequestBody @Valid @NonNull RegistrarDTO registrarDTO) {
        if (this.clienteRepo.findByTelefone(registrarDTO.getTelefone()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone já cadastrado");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registrarDTO.getSenha());
        
        Cliente novoCliente = new Cliente(registrarDTO.getNome(), registrarDTO.getTelefone(), encryptedPassword,
            registrarDTO.getEmail(), UserRole.USER, registrarDTO.getCpf());

        novoCliente = this.clienteRepo.save(novoCliente);
        
        registrarDTO.setSenha(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarDTO);
    }

    @Operation(summary = "Retorna informações sobre o usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = LerClienteDTO.class)) }),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar usuário logado", content = @Content) })
    @GetMapping("/eu")
    @SecurityRequirement(name = "bearerAuth")
    public LerClienteDTO eu() {
        Cliente cliente = (Cliente) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new LerClienteDTO(cliente.getNome(), cliente.getTelefone(), cliente.getEmail(), cliente.getCpf());
    }
}
