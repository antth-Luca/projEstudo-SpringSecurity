package io.github.antth_Luca.api.dto;

public record LoginResponseDTO(
    String accessToken,
    String refreshToken
) { }
