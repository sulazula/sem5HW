package pl.sulazula.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import pl.sulazula.demo.entity.User;
import pl.sulazula.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository ur;

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

}
