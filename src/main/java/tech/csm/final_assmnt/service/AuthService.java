package tech.csm.final_assmnt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.csm.final_assmnt.model.User;
import tech.csm.final_assmnt.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {

//    checking login credentials frm the DB

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(String username, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new Exception("Invalid username or password");
        }

        User user = optionalUser.get();

        if (!user.getIsActive()) {
            throw new Exception("User is not active");
        }

        if (!passwordEncoder.matches(password,user.getPasswordHash())) {
            throw new Exception("Invalid username or password");
        }

        return user;
    }
}

