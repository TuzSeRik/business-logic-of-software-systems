package dev.tuzserik.business.logic.of.software.systems.lab2.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import java.util.ArrayList;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.User;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Cart;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.UserService;
import dev.tuzserik.business.logic.of.software.systems.lab2.repositories.CartRepository;

@RequiredArgsConstructor @Component
public class Initialisation implements ApplicationListener<ContextRefreshedEvent> {
    private final UserService userService;
    private final CartRepository cartRepository;
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

            Cart emptyCart = new Cart();
            emptyCart.setId(new UUID(0, 0));
            emptyCart.setItemIds(new ArrayList<>());
            cartRepository.save(emptyCart);

            alreadySetup = true;
        }
    }
}
