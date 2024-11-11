package pl.sulazula.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sulazula.demo.entity.Project;
import pl.sulazula.demo.entity.User;
import pl.sulazula.demo.service.UsersProjectService;

import java.util.List;

@Controller
@RequestMapping("/project-repository")
@RequiredArgsConstructor
public class UserProjectController {

    private final UsersProjectService ups;

    /*@Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of users returned successfully",
                    content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No users found",
                    content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Users not found.\" }"))
            )
    })
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = ups.findAll();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @Operation(summary = "Get users by project ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of users returned successfully",
                    content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No users found for the specified project",
                    content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Users not found.\" }"))
            )
    })
    @GetMapping("/users-in-project/{id}")
    public ResponseEntity<List<User>> getUsersByProjectId(
            @Parameter(description = "ID of the project to get users from", required = true, example = "1")
            @PathVariable("id") Long projectId) {
        List<User> users = ups.getUserByProjectId(projectId);
        return users.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(users);
    }

    @Operation(summary = "Get projects by user ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of projects returned successfully",
                    content = @Content(schema = @Schema(implementation = Project.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No projects found for the specified user",
                    content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Projects not found.\" }"))
            )
    })
    @GetMapping("/projects-by-user/{id}")
    public ResponseEntity<List<Project>> getProjectsByUserId(
            @Parameter(description = "ID of the user to get projects for", required = true, example = "2")
            @PathVariable("id") Long userId) {
        List<Project> projects = ups.getProjectByUserId(userId);
        return projects.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(projects);
    }
*/
    @GetMapping
    public String getProjects(Model model) {
        List<Project> projects = ups.findAllProjects();

        model.addAttribute("projects", projects);
        return "project-repository";
    }

    @GetMapping("/users/{id}")
    public String getUsers(@PathVariable Long id, Model model) {
        List<User> users = ups.getUserByProjectId(id);
        Project project = ups.getProjectById(id);

        model.addAttribute("project", project);
        model.addAttribute("users", users);
        return "users-in-project";
    }

    @PostMapping("/user-delete/{id}/{pid}")
    public String deleteUser(@PathVariable Long id, @PathVariable Long pid) {
        ups.removeUserFromProject(pid, id);

        return "redirect:/project-repository/users/" + pid;
    }
}
