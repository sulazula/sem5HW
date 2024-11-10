package pl.sulazula.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sulazula.demo.entity.Project;
import pl.sulazula.demo.entity.User;
import pl.sulazula.demo.entity.enums.AppRole;
import pl.sulazula.demo.repository.UserRepository;
import pl.sulazula.demo.service.UsersProjectService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class MainWindowController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UsersProjectService usersProjectService;

    @GetMapping
    public ResponseEntity<?> dashboard(Principal principal) {
        User current = userRepository
                .findUserByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (current.getRole() == AppRole.USER) {
            List<Project> projects = usersProjectService.getProjectByUserId(current.getId());

            return ResponseEntity.ok(projects);
        }
        if (current.getRole() == AppRole.ADMIN) {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users);
        }
        return ResponseEntity.notFound().build();
    }
}
