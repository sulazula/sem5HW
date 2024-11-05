package pl.sulazula.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User`s ID", example = "3")
    private Long id;

    @Schema(description = "User`s name", example = "John")
    @Column(name = "name")
    private String name;

    @Schema(description = "Password your user have", example = "123qwerty")
    @Column(name = "password")
    private String password;

    @Schema(description = "Email your user have", example = "abc@def.com")
    @Column(name = "email")
    private String email;

    @Schema(description = "Role he has", example = "ADMIN or USER")
    @Column(name = "role")
    private String role;
}
