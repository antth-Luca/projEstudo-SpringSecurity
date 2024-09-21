package io.github.antth_Luca.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.antth_Luca.api.dto.AuthenticationDTO;
import io.github.antth_Luca.api.dto.LoginResponseDTO;
import io.github.antth_Luca.api.dto.RefreshDTO;
import io.github.antth_Luca.api.dto.RegisterDTO;
import io.github.antth_Luca.api.model.Cliente;
import io.github.antth_Luca.api.repository.ClienteRepository;
import io.github.antth_Luca.api.service.RefreshTokenService;
import io.github.antth_Luca.api.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins="*", maxAge=3600)
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var accessToken = tokenService.generateToken((Cliente) auth.getPrincipal());
        var refreshToken = refreshTokenService.generateRefreshToken((Cliente) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshDTO data) {
        String oldRefreshToken = data.refreshToken();
        
        try {
            // Valida o refresh token e retorna o CPF do cliente
            String cpfCliente = refreshTokenService.validateRefreshToken(oldRefreshToken);
            Cliente cliente = (Cliente) clienteRepo.findByCpf(cpfCliente);

            // Gera um novo access token usando o CPF
            String newAccessToken = tokenService.generateToken(cliente);
            // Gera um novo refresh token e atualiza o banco
            String newRefreshToken = refreshTokenService.updateRefreshToken(oldRefreshToken, cliente);

            // Retorna a resposta com o novo access token e o refresh token
            return ResponseEntity.ok(new LoginResponseDTO(newAccessToken, newRefreshToken));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal Cliente cliente) {
        refreshTokenService.deleteRefreshToken(cliente);
        return ResponseEntity.ok().body("O utilizador está deslogado");
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        if (this.clienteRepo.findByCpf(data.login()) != null) return ResponseEntity.badRequest().build();

        String hashPassword = new BCryptPasswordEncoder().encode(data.senha());
        Cliente newCliente = new Cliente(
            data.nome(),
            data.endereco(),
            data.login(),
            hashPassword,
            data.role()
        );

        this.clienteRepo.save(newCliente);

        return ResponseEntity.ok().build();
    }
}
