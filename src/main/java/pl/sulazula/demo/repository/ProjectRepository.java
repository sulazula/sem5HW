package pl.sulazula.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sulazula.demo.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
