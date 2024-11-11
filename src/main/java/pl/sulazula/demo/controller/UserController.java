package pl.sulazula.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sulazula.demo.entity.User;
import pl.sulazula.demo.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService us;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/create-user")
    public String createUser(Model model) {
        model.addAttribute("user", new User());

        return "user-create";
    }
    @PostMapping("/create-user")
    public String createUser(@ModelAttribute User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        us.addUser(user);

        return "redirect:/dashboard";
    }

    @PostMapping("/delete-user/{id}")
    public String deleteUser(@PathVariable Long id) {
        us.deleteUserById(id);

        return "redirect:/dashboard";
    }

    @PostMapping("/change-password")
    public String changePassword(Model model, Principal principal,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword) {

        User current = us
                .findUserByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(principal.getName()));

        if (!us.confirmPassword(current, oldPassword)) {
            model.addAttribute("message", "Your password is incorrect.");

            return "redirect:/dashboard/me";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("message", "Your password is incorrect.");

            return "redirect:/dashboard/me";
        }

        us.updatePassword(current, newPassword);

        return "redirect:/dashboard/me";
    }
}
