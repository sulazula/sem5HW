package pl.sulazula.demo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.sulazula.demo.entity.enums.AppRole;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User`s ID", example = "3")
    private Long id;

    @Schema(description = "User`s login", example = "Jenny Kitty")
    @Column(name = "username")
    private String username;

    @Schema(description = "Password your user have", example = "123qwerty")
    @Column(name = "password")
    private String password;

    @Schema(description = "Email your user have", example = "abc@def.com")
    @Column(name = "email")
    private String email;

    @Schema(description = "Role he has", example = "ADMIN or  USER")
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private AppRole role;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(role)
                .map(r -> (GrantedAuthority) () -> "ROLE_" + r.name())
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
