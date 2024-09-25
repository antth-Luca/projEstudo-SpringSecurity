package io.github.antth_Luca.api.security.bucket;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {
    // Configurando o bucket para permitir 10 requisições por minuto
    @SuppressWarnings("deprecation")
    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1))))
            .build();

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);  // Continua a execução da requisição
        } else {
            response.setStatus(429);  // Erro 429 (Too Many Requests)
            response.getWriter().write("Muitas requisicoes. Por favor, tente novamente mais tarde.");
        }
    }
}
