package dev.tuzserik.business.logic.of.software.systems.lab2.controllers;

import dev.tuzserik.business.logic.of.software.systems.lab2.model.Item;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.User;
import dev.tuzserik.business.logic.of.software.systems.lab2.requests.OrderCreatingRequest;
import dev.tuzserik.business.logic.of.software.systems.lab2.responses.CartStatusResponse;
import dev.tuzserik.business.logic.of.software.systems.lab2.responses.CatalogListResponse;
import dev.tuzserik.business.logic.of.software.systems.lab2.responses.OrderStatusResponse;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.CatalogService;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.OrderService;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor @RestController @RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CustomerController {
    private final CatalogService catalogService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/catalog")
    ResponseEntity<CatalogListResponse> getItemsByAttributes(@RequestParam Map<String, String> allRequestParams) {
        if (catalogService.verifyAttributes(
                // Type of an Item
                allRequestParams.remove("type"),
                // Attributes of an Item
                allRequestParams.keySet().stream().map(UUID::fromString).collect(Collectors.toSet())
            )
        ) {
            return new ResponseEntity<>(
                    new CatalogListResponse(
                            catalogService.findAllAppropriateItems(allRequestParams)
                            .stream().map(Item::getId).collect(Collectors.toList())
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cart/new")
    ResponseEntity<CartStatusResponse> getNewCart() {
        // TODO Method need to create new empty cart in database and return it ID and items in it
        return null;
    }

    @GetMapping("/cart")
    ResponseEntity<CartStatusResponse> getCart(@RequestParam UUID cartId) {
        // TODO We need to verify cart (if like in getItemsByAttributes) and it it is present
        // TODO return it's items (like now). Otherwise, return BAD_REQUEST
        return new ResponseEntity<>(
                new CartStatusResponse(
                        catalogService.checkCartPresence(cartId), catalogService.getCartItems(cartId)
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/cart/add")
    ResponseEntity<CartStatusResponse> addToCart(@RequestParam UUID itemId) {
        // TODO Method should verify presence of item in database and if check is succeeded
        // TODO it should add it to the cart
        return null;
    }

    @PostMapping("/cart/delete")
    ResponseEntity<CartStatusResponse> deleteFromCart(@RequestParam UUID itemId) {
        // TODO Method should verify presence of item in a cart and on success delete it from there
        return null;
    }

    @DeleteMapping("/cart")
    ResponseEntity<CartStatusResponse> cleanCart(@RequestParam UUID cartId) {
        // TODO Method should delete everything from cart
        return null;
    }

    @PostMapping("/order")
    ResponseEntity<OrderStatusResponse> createOrder(@RequestBody OrderCreatingRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        if (user != null) {
            // TODO We should create order with corresponding parameters
            // TODO from request and return information about it
            // TODO Should be noted, that we also discussed payment status parameter add to the order
            return null;
        }
        else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
