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

    public List<User> getUserByProjectId(Long projectId) {
        List<UsersProject> users = upr.findByProjectId(projectId);

        return users.stream()
                .map(UsersProject::getUserId)
                .map(ur::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<Project> getProjectByUserId(Long userId) {
        List<UsersProject> projects = upr.findByUserId(userId);

        return projects.stream()
                .map(UsersProject::getProjectId)
                .map(pr::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

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
