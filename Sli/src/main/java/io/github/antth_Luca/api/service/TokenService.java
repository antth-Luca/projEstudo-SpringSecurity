package io.github.antth_Luca.api.service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import io.github.antth_Luca.api.model.Cliente;

@Service
public class TokenService {
    @Value("${jwt.asym-key.private}")
    private RSAPrivateKey privateKey;

    @Value("${jwt.asym-key.public}")
    private RSAPublicKey publicKey;

    public String generateToken(Cliente cliente) {
        try {
            Algorithm algorithm = Algorithm.RSA256(null, privateKey);
            String token = JWT.create()
                    .withIssuer("${spring.application.name}")
                    .withSubject(cliente.getCpf())
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro durante a geração do token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            return JWT.require(algorithm)
                    .withIssuer("${spring.application.name}")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return "";
        } 
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusMinutes(5).toInstant(ZoneOffset.of("-03:00"));
    }
}
