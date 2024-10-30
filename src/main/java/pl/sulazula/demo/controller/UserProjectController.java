package pl.sulazula.demo.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.sulazula.demo.entity.Project;
import pl.sulazula.demo.entity.User;
import pl.sulazula.demo.service.UsersProjectService;

import java.util.List;

@Data
@Controller
@RequestMapping("/project-repository")
public class UserProjectController {

    @Autowired
    private UsersProjectService ups;

    @GetMapping("/users-in-project/{id}")
    public ResponseEntity<List> getUsersByProjectId(@PathVariable("id") Long projectId) {
        List<User> users = ups.getUserByProjectId(projectId);

        if (users.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/projects-by-user/{id}")
    public ResponseEntity<List> getProjectsByUserId(@PathVariable("id") Long userId) {
        List<Project> projects = ups.getProjectByUserId(userId);

        if (projects.isEmpty()) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(projects);
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addUserToProject(@RequestParam Long projectId, @RequestParam Long userId) {
        try {
            ups.addUserToProject(projectId, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }

    @DeleteMapping("/delete-user-from-project")
    public ResponseEntity<String> deleteUserFromProject(@RequestParam Long projectId, @RequestParam Long userId) {
        try {
            ups.removeUserFromProject(projectId, userId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully");
    }
}
