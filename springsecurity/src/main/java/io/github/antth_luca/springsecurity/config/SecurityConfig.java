package io.github.antth_luca.springsecurity.config;

import java.beans.Customizer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    private SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())  // Todas as requisições devem ser autenticadas.
            .csrf(csrf -> csrf.disable())  // Desativando localmente. NÃO SUBA DESTA MANEIRA!
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))  // Utilizando o JWT
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));  // O JWT não precisa de dados em sessão.

        return http.build();
    }
}
