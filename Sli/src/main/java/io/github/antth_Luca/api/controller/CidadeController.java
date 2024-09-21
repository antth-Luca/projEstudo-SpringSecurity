package io.github.antth_Luca.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.antth_Luca.api.dto.CidadeDTO;
import io.github.antth_Luca.api.model.Cidade;
import io.github.antth_Luca.api.repository.CidadeRepository;
import io.github.antth_Luca.api.responses.Response;

@RestController
@RequestMapping("/api/cidade")
@CrossOrigin(origins="*", maxAge=3600)
public class CidadeController {
    @Autowired
    private CidadeRepository repository;

    // Converter Cidade (entidade) para CidadeDTO
    private CidadeDTO toDTO(Cidade cidade) {
        return new CidadeDTO(cidade.getNome(), cidade.getUf());
    }

    @PostMapping
    public ResponseEntity<Response<CidadeDTO>> inserir(@RequestBody Cidade cidade) {
        Cidade novaCidade = repository.save(cidade);
        Response<CidadeDTO> response = Response.success(toDTO(novaCidade), "Cidade criada com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<CidadeDTO> getAll() {
        List<Cidade> cidades = repository.findAll();
        return cidades.stream().map(this::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<CidadeDTO>> getById(@PathVariable Long id) {
        return repository.findById(id)
            .map(cidade -> ResponseEntity.ok(Response.success(toDTO(cidade), "Cidade encontrada")))
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.error(List.of("Cidade não encontrada"), "Erro", HttpStatus.NOT_FOUND.value())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<CidadeDTO>> update(@PathVariable Long id, @RequestBody CidadeDTO cidadeDTO) {
        return repository.findById(id)
            .map(cidade -> {
                cidade.setNome(cidadeDTO.nome());
                cidade.setUf(cidadeDTO.uf());
                Cidade cidadeAtualizada = repository.save(cidade);
                Response<CidadeDTO> response = Response.success(toDTO(cidadeAtualizada), "Cidade atualizada com sucesso");
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Response.error(List.of("Cidade não encontrada"), "Erro", HttpStatus.NOT_FOUND.value())));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        repository.findById(id)
            .ifPresent(repository::delete);
    }
}
