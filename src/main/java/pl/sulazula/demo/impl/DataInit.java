package pl.sulazula.demo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.sulazula.demo.entity.Project;
import pl.sulazula.demo.entity.User;
import pl.sulazula.demo.entity.UsersProject;
import pl.sulazula.demo.entity.enums.AppRole;
import pl.sulazula.demo.repository.ProjectRepository;
import pl.sulazula.demo.repository.UserRepository;
import pl.sulazula.demo.repository.UsersProjectRepository;

import java.time.LocalDate;
import java.util.Date;

@Component
public class DataInit implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UsersProjectRepository usersProjectRepository;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findAll().isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.com");
            admin.setRole(AppRole.ADMIN);
            userRepository.save(admin);

            User user = new User();
            user.setUsername("user");
            user.setEmail("user@user.com");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRole(AppRole.USER);
            userRepository.save(user);

            Project project = new Project();
            project.setName("project");
            project.setDescription("description");
            project.setCreatedDate(LocalDate.now());
            projectRepository.save(project);

            UsersProject usersProject = new UsersProject();
            usersProject.setUserId(user.getId());
            usersProject.setProjectId(project.getId());
            usersProjectRepository.save(usersProject);
        }
    }
}
