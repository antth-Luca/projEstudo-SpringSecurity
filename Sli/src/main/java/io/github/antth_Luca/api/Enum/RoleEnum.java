package io.github.antth_Luca.api.Enum;

public enum RoleEnum {
    ADMIN("admin"),
    COMMON("comum");

    private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
