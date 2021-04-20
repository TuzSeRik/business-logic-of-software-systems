package dev.tuzserik.business.logic.of.software.systems.lab1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import dev.tuzserik.business.logic.of.software.systems.lab1.repositories.UserRepository;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.User;

@AllArgsConstructor @Service
public class AuthorisationService {
    private final UserRepository userRepository;

    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    public User saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
}
