package pl.sulazula.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sulazula.demo.entity.Project;
import pl.sulazula.demo.entity.User;
import pl.sulazula.demo.entity.UsersProject;
import pl.sulazula.demo.service.UsersProjectService;

import java.util.List;

@Data
@RestController
@RequestMapping("/project-repository")
public class UserProjectController {

    @Autowired
    private UsersProjectService ups;

    @Operation(summary = "Get all users")
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

    @Operation(summary = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Check if the data is correct.\" }"))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Backend code error.\" }"))
            )
    })
    @PostMapping("/add-user")
    public ResponseEntity<String> addUser(
            @Parameter(description = "User details to add", required = true)
            @RequestBody User user) {
        try {
            ups.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request parameters");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Add user to project")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User added to the project successfully.",
                    content = @Content(schema = @Schema(implementation = UsersProject.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request parameters",
                    content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Check if the data is correct.\" }"))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject(value = "{ \"message\": \"Backend code error.\" }"))
            )
    })
    @PostMapping("/add-user-to-project")
    public ResponseEntity<String> addUserToProject(
            @Parameter(description = "ID of the project to add the user to", required = true, example = "2")
            @RequestParam Long projectId,
            @Parameter(description = "ID of the user to add", required = true, example = "5")
            @RequestParam Long userId) {
        try {
            ups.addUserToProject(projectId, userId);
            return ResponseEntity.ok().body("User added to project successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid request parameters");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Delete user from project")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User deleted successfully",
                    content = @Content(examples = @ExampleObject(value = "{ \"message\": \"User deleted.\" }"))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "ID of user you want to delete not found",
                    content = @Content(examples = @ExampleObject(value = "{ \"message\": \"User not found.\" }"))
            )
    })
    @DeleteMapping("/delete-user-from-project")
    public ResponseEntity<String> deleteUserFromProject(
            @Parameter(description = "ID of the project to remove user from", required = true, example = "2")
            @RequestParam Long projectId,
            @Parameter(description = "ID of the user to delete", required = true, example = "5")
            @RequestParam Long userId) {
        try {
            ups.removeUserFromProject(projectId, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
