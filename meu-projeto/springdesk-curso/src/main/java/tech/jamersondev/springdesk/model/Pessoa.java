package tech.jamersondev.springdesk.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import tech.jamersondev.springdesk.Enums.Role;

@Entity
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String senha;
    private String img_path;
    private Role role;

    // Constructors
    public Pessoa() {
    }

    public Pessoa(Integer id, String name, String email, String senha, String img_path, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.img_path = img_path;
        this.role = role;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
