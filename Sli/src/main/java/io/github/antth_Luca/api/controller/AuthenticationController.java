package io.github.antth_Luca.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.antth_Luca.api.controller.Record.AuthenticationDTO;
import io.github.antth_Luca.api.controller.Record.LoginResponseDTO;
import io.github.antth_Luca.api.controller.Record.RegisterDTO;
import io.github.antth_Luca.api.infra.security.TokenService;
import io.github.antth_Luca.api.model.Cliente;
import io.github.antth_Luca.api.repository.ClienteRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.senha());  // Ele usa os métodos do repository para conseguir login e senha.
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Cliente) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
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
