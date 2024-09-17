package io.github.antth_Luca.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.antth_Luca.api.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
