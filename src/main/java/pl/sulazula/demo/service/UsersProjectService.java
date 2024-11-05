package pl.sulazula.demo.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sulazula.demo.entity.Project;
import pl.sulazula.demo.entity.User;
import pl.sulazula.demo.entity.UsersProject;
import pl.sulazula.demo.repository.ProjectRepository;
import pl.sulazula.demo.repository.UserRepository;
import pl.sulazula.demo.repository.UsersProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class UsersProjectService {

    @Autowired
    private UserRepository ur;
    @Autowired
    private ProjectRepository pr;
    @Autowired
    private UsersProjectRepository upr;

    /**
     *  В потоке данных связь юзер-проект преобразуется в айди юзера,
     *  затем по айдишнику берется сам юзер в оболочке optional(user or null),
     *  затем фильтр отбрасывает пустые optional а мапа достает из оставшихся
     *  сами юзеры и собирает в коллекцию
     *  ВАЖНО!!! На старте тип UsersProject, в конце - User
     *  @param projectId
     */
    public List<User> getUserByProjectId(Long projectId) {
        List<UsersProject> users = upr.findByProjectId(projectId);

        return users.stream()
                .map(UsersProject::getUserId)
                .map(ur::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     *  В потоке данных связь юзер-проект преобразуется в айди проекта,
     *  затем по айдишнику берется сам проект в оболочке optional(project or null),
     *  затем фильтр отбрасывает пустые optional а мапа достает из оставшихся
     *  сами проекты и собирает в коллекцию
     *  ВАЖНО!!! На старте тип UsersProject, в конце - Project
     *  @param userId
     */
    public List<Project> getProjectByUserId(Long userId) {
        List<UsersProject> projects = upr.findByUserId(userId);

        return projects.stream()
                .map(UsersProject::getProjectId)
                .map(pr::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<User> findAll(){
        List<User> users = ur.findAll();

        return users;
    }

    /**
     *
     * @param projectId
     * @param userId
     * На старте метод вытягивает юзера и проект в оболочке
     * optional(user/project or null), если какой-то из них пуст,
     * отбрасывает исключение.
     * Затем связь создается и сохраняется
     */
    public void addUserToProject(Long projectId, Long userId) {
        Optional<User> user = ur.findById(userId);
        Optional<Project> project = pr.findById(projectId);

        if (user.isEmpty()) throw new EntityNotFoundException("User with id" + userId + " not found");
        if (project.isEmpty()) throw new EntityNotFoundException("Project with id" + projectId + " not found");

        UsersProject relation = new UsersProject();
        relation.setUserId(userId);
        relation.setProjectId(projectId);
        upr.save(relation);
    }

    /**
     * То же самое, что и в методе выше
     * @see UsersProjectService#addUserToProject(Long, Long) 
     * @param projectId
     * @param userId
     */
    public void removeUserFromProject(Long projectId, Long userId) {
        Optional<User> user = ur.findById(userId);
        Optional<Project> project = pr.findById(projectId);

        if (user.isEmpty()) throw new EntityNotFoundException("User with id" + userId + " not found");
        if (project.isEmpty()) throw new EntityNotFoundException("Project with id" + projectId + " not found");

        Optional<UsersProject> relation = upr.findByUserIdAndProjectId(userId, projectId);
        if (relation.isEmpty()) throw new EntityNotFoundException("User with id" + userId + " not found");

        upr.delete(relation.get());
    }
}
