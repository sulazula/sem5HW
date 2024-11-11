package pl.sulazula.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sulazula.demo.entity.Project;
import pl.sulazula.demo.entity.User;
import pl.sulazula.demo.entity.enums.AppRole;
import pl.sulazula.demo.service.UserService;
import pl.sulazula.demo.service.UsersProjectService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class MainWindowController {

    @Autowired
    UsersProjectService usersProjectService;
    @Autowired
    UserService userService;

    @GetMapping
    public String dashboard(Principal principal, Model model) {
        User current = userService
                .findUserByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (current.getRole() == AppRole.USER) {
            List<Project> projects = usersProjectService.getProjectByUserId(current.getId());

            model.addAttribute("projects", projects);
            return "dashboard-user";
        }
        if (current.getRole() == AppRole.ADMIN) {
            List<User> users = userService.findAll();

            model.addAttribute("usersList", users);
            return "dashboard-admin";
        }
        return "error";
    }

    @GetMapping("/project/{id}")
    public String showProjectDetails(@PathVariable Long id, Model model) {
        Project project = usersProjectService.getProjectById(id);

        model.addAttribute("project", project);
        return "project-details";
    }
}
