package com.imobarea.api.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.imobarea.api.models.entity.Cliente;

@Service
public class TokenService {
    private String secret = "secret";

    public String generateToken(Cliente cliente) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                .withIssuer("ImobArea")
                .withSubject(cliente.getTelefone())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);
            
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao criar token");
        }
    }

    public String decodeToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String subject = JWT.require(algorithm)
                .withIssuer("ImobArea")
                .build()
                .verify(token)
                .getSubject();
            return subject;
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Erro ao decodificar token");
        }
    }
}
