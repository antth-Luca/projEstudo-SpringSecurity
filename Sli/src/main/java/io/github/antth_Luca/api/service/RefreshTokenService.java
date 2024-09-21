package io.github.antth_Luca.api.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import io.github.antth_Luca.api.model.Cliente;
import io.github.antth_Luca.api.model.RefreshToken;
import io.github.antth_Luca.api.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${jwt.sym-key}")
    private String symKey;

    @Value("${time.expiration.refresh-token}")
    private Integer refreshExpiration;

    @Autowired
    private RefreshTokenRepository refreshTokenRepo;

    @Transactional
    public String generateRefreshToken(Cliente cliente) {
        try {
            String jti = UUID.randomUUID().toString();

            Algorithm algorithm = Algorithm.HMAC256(symKey);
            String jwtRefreshToken = JWT.create()
                    .withIssuer(appName)
                    .withClaim("refresh_token_id", jti)
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm);

            RefreshToken nodeRefreshToken = new RefreshToken(
                jti,
                cliente,
                LocalDateTime.now().toInstant(ZoneOffset.of("-03:00")),
                false
            );
            refreshTokenRepo.save(nodeRefreshToken);

            return jwtRefreshToken;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro durante a geração do refresh token", e);
        }
    }

    public String validateRefreshToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(symKey);
            var decodedJWT = JWT.require(algorithm)
                    .withIssuer(appName)
                    .build()
                    .verify(token);

            RefreshToken nodeRefreshToken = refreshTokenRepo.findByJwtId(decodedJWT.getClaim("refresh_token_id").asString())
                .orElseThrow(() -> new RuntimeException("Token inválido ou não encontrado"));

            if (nodeRefreshToken.is_revoked()) {
                throw new RuntimeException("Token expirado ou revogado");
            }

            return nodeRefreshToken.getCliente().getCpf();

        } catch (JWTVerificationException e) {
            return "";
        }
    }

    @Transactional
    public String updateRefreshToken(String oldRefreshToken, Cliente cliente) {
        Algorithm algorithm = Algorithm.HMAC256(symKey);
        var decodedJWT = JWT.require(algorithm)
                .withIssuer(appName)
                .build()
                .verify(oldRefreshToken);

        refreshTokenRepo.deleteByJwtId(decodedJWT.getClaim("refresh_token_id").asString());
        return generateRefreshToken(cliente);
    }

    public void deleteRefreshToken(Cliente cliente) {
        refreshTokenRepo.deleteByClienteCpf(cliente.getCpf());
    }

    private Instant genExpirationDate() {
        return LocalDateTime.now().plusHours(refreshExpiration).toInstant(ZoneOffset.of("-03:00"));
    }
}
