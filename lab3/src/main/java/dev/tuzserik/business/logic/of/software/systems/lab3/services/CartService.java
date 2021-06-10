package dev.tuzserik.business.logic.of.software.systems.lab3.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab3.repositories.CartRepository;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Cart;

@AllArgsConstructor @Service
public class CartService {
    public CartRepository cartRepository;

    public Cart createNewCart() {
        return cartRepository.save(new Cart());
    }

    public Cart getCartById(UUID id) {
       return cartRepository.getOne(id);
    }

    public Cart saveCart(Cart cart){
        return cartRepository.save(cart);
    }
}
