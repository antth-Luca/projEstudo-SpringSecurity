package io.github.antth_Luca.api.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import lombok.Getter;
import lombok.Setter;

@Node
@Getter
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;

    @Property("jti")
    private String jwtId;

    @Relationship(type = "HAS_REFRESH_TOKEN")
    private Cliente cliente;

    @Property("iat")
    private Instant issuedAt;

    @Setter
    @Property("is_revoked")
    private boolean is_revoked = false;

    // Constructor
    public RefreshToken(String jwtId, Cliente cliente, Instant issuedAt, boolean is_revoked) {
        this.jwtId = jwtId;
        this.cliente = cliente;
        this.issuedAt = issuedAt;
        this.is_revoked = is_revoked;
    }
}
