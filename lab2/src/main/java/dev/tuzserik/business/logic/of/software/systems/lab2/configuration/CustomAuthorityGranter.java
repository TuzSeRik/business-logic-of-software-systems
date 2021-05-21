package dev.tuzserik.business.logic.of.software.systems.lab2.configuration;

import org.springframework.stereotype.Component;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import java.security.Principal;
import java.util.Set;
import java.util.HashSet;

@Component
public class CustomAuthorityGranter implements AuthorityGranter {
    @Override
    public Set<String> grant(Principal principal) {
        Set<String> roles = new HashSet<>();

        roles.add(principal.getName());

        return roles;
    }
}
