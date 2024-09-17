package tech.jamersondev.springdesk.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import tech.jamersondev.springdesk.Enums.Role;

@Entity
public class Cliente extends Pessoa {
    @OneToMany
    private List<Chamado> chamados = new ArrayList<>();

    // Constructors
    public Cliente(Integer id, String name, String email, String senha, String img_path, Role role, List<Chamado> chamados) {
        super(id, name, email, senha, img_path, role);
        this.chamados = chamados;
    }

    public Cliente() { }

    // Getters e Setters
    public List<Chamado> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamado> chamados) {
        this.chamados = chamados;
    }
}
