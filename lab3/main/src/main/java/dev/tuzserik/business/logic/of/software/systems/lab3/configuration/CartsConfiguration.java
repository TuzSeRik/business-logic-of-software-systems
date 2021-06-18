package dev.tuzserik.business.logic.of.software.systems.lab3.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;
import dev.tuzserik.business.logic.of.software.systems.lab3.services.CartService;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Cart;

@Configuration @EnableScheduling @AllArgsConstructor
public class CartsConfiguration {
    private final CartService cartService;

    @Transactional @Scheduled(fixedRate = 3600000)
    void deleteOldCarts() {
        Set<Cart> carts = cartService.findEmptyCarts();

        if (carts != null && !carts.isEmpty()) {
            cartService.deleteCarts(carts);
        }

        System.out.println("Empty Carts deleted!");
    }
}
