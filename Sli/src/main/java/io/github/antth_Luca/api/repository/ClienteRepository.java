package io.github.antth_Luca.api.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.security.core.userdetails.UserDetails;

import io.github.antth_Luca.api.model.Cliente;

public interface ClienteRepository extends Neo4jRepository<Cliente, Long> {
    UserDetails findByCpf(String cpf);
}
