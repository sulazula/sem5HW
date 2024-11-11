package pl.sulazula.demo.entity.DTO;

import pl.sulazula.demo.entity.enums.AppRole;

public class UserDTO {
    private String username;
    private String email;
    private AppRole role;

    public UserDTO(String username, String email, AppRole role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public AppRole getRole() {
        return this.role;
    }
}
