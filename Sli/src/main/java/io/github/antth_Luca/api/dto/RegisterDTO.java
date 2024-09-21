package io.github.antth_Luca.api.dto;

import io.github.antth_Luca.api.Enum.RoleEnum;

public record RegisterDTO(
    String nome,
    String endereco,
    String login,
    String senha,
    RoleEnum role
) { }
