package io.github.antth_Luca.api.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import lombok.Data;

@Node
@Data
public class Cidade {
    @Id
    @GeneratedValue
    private Long id;

    @Property("nome")
    private String nome;

    @Property("uf")
    private String uf;
}
