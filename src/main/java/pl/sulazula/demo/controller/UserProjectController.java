package pl.sulazula.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RestController
@RequestMapping("/project-repository")
public class UserProjectController {

    @Autowired
    private UsersProjectService ups;
    /**
     * ResponseEntity это HTTP-Ответ сервера, то есть коды ошибок, либо статусы,
     * а также наши собственные данные в body.
     * То есть мы возвращаем тех же юзеров, но вместе с кодами по типу
     * 404 NOT FOUND, 500 INTERNAL SERVER ERROR и так далее
     *
     * @return Response 200 OK c телом из юзеров
     */
    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = ups.findAll();

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get users in project by project`s ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users returned successfully"),
            @ApiResponse(responseCode = "404", description = "Empty, users not found")
    })
    @GetMapping("/users-in-project/{id}")
    public ResponseEntity<List> getUsersByProjectId(@PathVariable("id") Long projectId) {
        List<User> users = ups.getUserByProjectId(projectId);

        if (users.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get project by user`s ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of projects returned successfully"),
            @ApiResponse(responseCode = "404", description = "Empty, projects not found")
    })
    @GetMapping("/projects-by-user/{id}")
    public ResponseEntity<List> getProjectsByUserId(@PathVariable("id") Long userId) {
        List<Project> projects = ups.getProjectByUserId(userId);

        if (projects.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(projects);
    }

    /**
     *
     * Если операция успешна, возвращается статус "СОЗДАНО" (201) с сообщением.
     * @return ResponseBody CREATED(code 201) с телом "User added"
     */
    @Operation(summary = "Add user to project by proj ID and user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/add-user")
    public ResponseEntity<String> addUserToProject(@RequestParam Long projectId, @RequestParam Long userId) {
        try {
            ups.addUserToProject(projectId, userId);
        } catch (IllegalArgumentException e) {
            // Вернём 400 для некорректных параметров
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request parameters");
        } catch (Exception e) {
            e.printStackTrace();
            // Вернём 500 для внутренних ошибок
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("User added successfully");
    }

    /**
     * Если операция успешна, возвращает 204 NO CONTENT с текстом
     * @return ResponseEntity NO CONTENT(code 204) с телом "User deleted"
     */
    @Operation(summary = "Delete user from project by proj ID and user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User deleted successfully")
    })
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
