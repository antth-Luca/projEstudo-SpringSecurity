package io.github.antth_Luca.api.model;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Valid
    @NotEmpty(message="{campo.nome.obrigatorio}")
    private String nome;

    @Column
    private String endereco;

    @Column(nullable=false, length=11)
    @Valid
    @NotNull(message="{campo.cpf.obrigatorio}")
    @CPF(message="{campo.cpf.invalido}")
    private String cpf;
}
