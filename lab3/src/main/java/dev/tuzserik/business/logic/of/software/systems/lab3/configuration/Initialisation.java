package dev.tuzserik.business.logic.of.software.systems.lab3.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import dev.tuzserik.business.logic.of.software.systems.lab3.services.UserService;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.User;

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
            userService.saveUserForAFirstTime(administrator);

            alreadySetup = true;
        }
    }
}
