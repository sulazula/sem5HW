package pl.sulazula.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sulazula.demo.entity.UsersProject;

import java.util.List;
import java.util.Optional;

public interface UsersProjectRepository extends JpaRepository<UsersProject, Long> {

    /**
     * Spring самостоятельно создает SQL-Запрос,
     * достаточно только правильно написать его название, а
     * логика создастся автоматически.
     */
    List<UsersProject> findByProjectId(Long projectId);
    List<UsersProject> findByUserId(Long userId);
    Optional<UsersProject> findByUserIdAndProjectId(Long userId, Long projectId);
}
