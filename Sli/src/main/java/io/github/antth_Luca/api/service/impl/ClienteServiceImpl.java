package io.github.antth_Luca.api.service.impl;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import io.github.antth_Luca.api.model.Cliente;
import io.github.antth_Luca.api.repository.ClienteRepository;
import io.github.antth_Luca.api.responses.Response;
import io.github.antth_Luca.api.service.ClienteService;

@Component
public class ClienteServiceImpl implements ClienteService {
    @Autowired
    private ClienteRepository repository;

    @Override
    public ResponseEntity<Response<Cliente>> salvar(@Valid Cliente cliente, BindingResult result) {
        Response<Cliente> response = new Response<Cliente>();
        response.setData(cliente);
        if (result.hasErrors()) {
            for (ObjectError erros: result.getAllErrors()) {
                response.getErrors().add(erros.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(response);
        }

        repository.save(cliente);
        return ResponseEntity.ok(response);
    }

    @Override
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @Override
    public ResponseEntity<Response<Cliente>> getById(Integer id) {
        Response<Cliente> response = new Response<Cliente>();
        Cliente obj = null;
        try {
            obj = repository.findById(id).get();
        } catch (NullPointerException ex) {
            response.getErrors().add("{entidade.cliente.invalido}");
        } catch (Exception ex) {
            response.getErrors().add("{entidade.cliente.invalido}");
        }

        response.setData(obj);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response<Cliente>> deleteById(Integer id) {
        Response<Cliente> response = new Response<Cliente>();
        Cliente obj = null;
        try {
            obj = repository.findById(id).get();
            repository.delete(obj);
        } catch (NullPointerException ex) {
            response.getErrors().add("{entidade.cliente.invalido}");
        } catch (Exception ex) {
            response.getErrors().add("{entidade.cliente.invalido}");
        }

        response.setData(obj);
        return ResponseEntity.ok(response);
    }
}
