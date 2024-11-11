package pl.sulazula.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sulazula.demo.entity.User;
import pl.sulazula.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository ur;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        ur.save(user);

        return user;
    }

    public List<User> findAll(){
        List<User> users = ur.findAll();

        return users;
    }

    public void deleteUserById(Long id) {

        ur.findById(id).ifPresent(ur::delete);
    }

    public Optional<User> findUserByUsername(String username) {

        return ur.findUserByUsername(username);
    }

    public boolean confirmPassword(User user, String confirmPassword) {
        return passwordEncoder.matches(confirmPassword, user.getPassword());
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));

        ur.save(user);
    }

}
