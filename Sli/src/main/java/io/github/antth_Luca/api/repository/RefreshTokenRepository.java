package io.github.antth_Luca.api.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import io.github.antth_Luca.api.model.RefreshToken;


public interface RefreshTokenRepository extends Neo4jRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByJwtId(String jwtId);

    void deleteByJwtId(String jwtId);

    @Query("MATCH (c:Cliente)-[r:HAS_REFRESH_TOKEN]->(t:RefreshToken) WHERE c.cpf = $clienteCpf DELETE r, t")
    void deleteByClienteCpf(String clienteCpf);
}
