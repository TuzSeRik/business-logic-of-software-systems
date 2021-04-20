package dev.tuzserik.business.logic.of.software.systems.lab1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.User;

@AllArgsConstructor @Transactional @Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AuthorisationService authorisationService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = authorisationService.findUserByLogin(login);
        return new org.springframework.security.core.userdetails.User(
            user.getLogin(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
