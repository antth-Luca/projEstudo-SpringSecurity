package io.github.antth_Luca.api.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import io.github.antth_Luca.api.model.Cidade;

public interface CidadeRepository extends Neo4jRepository<Cidade, Long>{ }
