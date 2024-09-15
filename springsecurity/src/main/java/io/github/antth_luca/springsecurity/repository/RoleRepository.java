package io.github.antth_luca.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.antth_luca.springsecurity.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> { }