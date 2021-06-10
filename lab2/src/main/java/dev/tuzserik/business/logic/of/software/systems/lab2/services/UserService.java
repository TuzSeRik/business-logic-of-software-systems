package dev.tuzserik.business.logic.of.software.systems.lab2.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import dev.tuzserik.business.logic.of.software.systems.lab2.repositories.UserRepository;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.User;

@AllArgsConstructor @Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public boolean userExists(String username) {
        return findUserByUsername(username) != null;
    }

    public User saveUserForAFirstTime(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean verifyUser(String username, String password) {
        User user = userRepository.findUserByUsername(username);

        if (user != null)
            return passwordEncoder.matches(password, user.getPassword());

        return false;
    }

    public String getUserRoleByUsername(String username) {
        return findUserByUsername(username).getRole().name();
    }
}
