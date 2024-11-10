package pl.sulazula.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sulazula.demo.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
