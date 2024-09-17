package io.github.antth_Luca.api.model;

import org.hibernate.validator.constraints.br.CPF;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import io.github.antth_Luca.api.Enum.RoleEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Node
@Data
public class Cliente {
    @Id
    @GeneratedValue
    private Long id;

    @Property("nome")
    @Valid
    @NotEmpty(message="{campo.nome.obrigatorio}")
    private String nome;

    @Property("endereco")
    private String endereco;

    @Property("cpf")
    @Valid
    @NotNull(message="{campo.cpf.obrigatorio}")
    @CPF(message="{campo.cpf.invalido}")
    private String cpf;

    @Property("role")
    @NotNull(message = "{campo.role.obrigatorio}")
    private RoleEnum role;
}
