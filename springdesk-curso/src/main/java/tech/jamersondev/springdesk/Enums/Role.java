package tech.jamersondev.springdesk.Enums;

public enum Role {
    ADMIN("Administrador"),
    TECNICO("Tecnico"),
    CLIENTE("Cliente");

    private String role;

    private Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
