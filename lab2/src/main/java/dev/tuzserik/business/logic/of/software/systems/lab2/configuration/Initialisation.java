package dev.tuzserik.business.logic.of.software.systems.lab2.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.User;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.UserService;

@RequiredArgsConstructor @Component
public class Initialisation implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;
    private boolean alreadySetup;

    @Override
    public void onApplicationEvent(@Nullable ContextRefreshedEvent contextRefreshedEvent) {
        if (!alreadySetup) {
            User administrator = new User();
            administrator.setUsername("root");
            administrator.setPassword("root");
            administrator.setRole(User.Role.ROLE_ROOT);
            administrator.setGivenName("Dmitriy");
            administrator.setFamilyName("Borisovich");
            userService.saveUser(administrator);

            alreadySetup = true;
        }
    }
}
