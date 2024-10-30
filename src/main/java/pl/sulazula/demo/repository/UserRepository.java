package pl.sulazula.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sulazula.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
