package io.github.antth_Luca.api.model;

import java.util.Collection;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.github.antth_Luca.api.Enum.RoleEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Node
@Data
public class Cliente implements UserDetails {
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

    @Property("senha")
    @NotNull(message = "{campo.senha.obrigatorio}")
    private String senha;

    @Property("role")
    @NotNull(message = "{campo.role.obrigatorio}")
    private RoleEnum role;

    // Constructors
    public Cliente() { }

    public Cliente(@Valid @NotEmpty(message = "{campo.nome.obrigatorio}") String nome, String endereco,
            @Valid @NotNull(message = "{campo.cpf.obrigatorio}") @CPF(message = "{campo.cpf.invalido}") String cpf,
            @NotNull(message = "{campo.senha.obrigatorio}") String senha,
            @NotNull(message = "{campo.role.obrigatorio}") RoleEnum role) {
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.senha = senha;
        this.role = role;
    }

    // Implementação dos métodos do UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // IF e ELSE para retornar a Role do usuário, lembrando que o nível acima, também retorna os níveis abaixo
        if (this.role == RoleEnum.ADMIN) return List.of(
            new SimpleGrantedAuthority("ROLE_ADMIN"),
            new SimpleGrantedAuthority("ROLE_COMMON")
            );
        else return List.of(
            new SimpleGrantedAuthority("ROLE_COMMON")
            );
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return cpf;
    }
}
