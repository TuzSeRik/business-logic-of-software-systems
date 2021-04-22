package dev.tuzserik.business.logic.of.software.systems.lab2.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Cart;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.User;
import dev.tuzserik.business.logic.of.software.systems.lab2.repositories.CartRepository;
import dev.tuzserik.business.logic.of.software.systems.lab2.repositories.UserRepository;

@RequiredArgsConstructor @Component
public class Loader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private boolean alreadySetup;

    @Override
    public void onApplicationEvent(@Nullable ContextRefreshedEvent contextRefreshedEvent) {
        if (!alreadySetup) {
            User administrator = new User();
            administrator.setLogin("root");
            administrator.setPassword(passwordEncoder.encode("root"));
            userRepository.save(administrator);

            Cart emptyCart = new Cart();
            emptyCart.setId(new UUID(0, 0));
            emptyCart.setItemIds(new ArrayList<>());
            cartRepository.save(emptyCart);

            alreadySetup = true;
        }
    }
}
