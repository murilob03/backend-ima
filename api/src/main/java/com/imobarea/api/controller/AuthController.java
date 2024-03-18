package com.imobarea.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.imobarea.api.models.dto.CriarClienteDTO;
import com.imobarea.api.models.dto.LoginDTO;
import com.imobarea.api.models.entity.Cliente;
import com.imobarea.api.models.entity.UserRole;
import com.imobarea.api.repositories.ClienteRepositorio;
import com.imobarea.api.services.TokenService;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO authDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.getTelefone(), authDTO.getSenha());
        var auth = this.authManager.authenticate(usernamePassword);

        String token = this.tokenService.generateToken((Cliente) auth.getPrincipal());

        return ResponseEntity.status(200).body("token: " + token);
    }

    @PostMapping("/registrar")
    public Cliente registrar(@RequestBody @Valid @NonNull CriarClienteDTO criarClienteDTO) {
        if (this.clienteRepo.findByTelefone(criarClienteDTO.getTelefone()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone já cadastrado");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(criarClienteDTO.getSenha());
        Cliente novoCliente = new Cliente(criarClienteDTO.getNome(), criarClienteDTO.getTelefone(), encryptedPassword, 
                criarClienteDTO.getEmail(), UserRole.USER, criarClienteDTO.getCpf());

        novoCliente = this.clienteRepo.save(novoCliente);

        return novoCliente;
    }
}
