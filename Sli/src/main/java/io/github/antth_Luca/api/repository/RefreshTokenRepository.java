package io.github.antth_Luca.api.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import io.github.antth_Luca.api.model.RefreshToken;


public interface RefreshTokenRepository extends Neo4jRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByJwtId(String jwtId);
}
