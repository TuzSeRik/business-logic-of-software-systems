package dev.tuzserik.business.logic.of.software.systems.lab3.controllers;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.stream.Collectors;
import java.util.*;
import java.time.ZonedDateTime;
import dev.tuzserik.business.logic.of.software.systems.lab3.services.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.requests.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.responses.*;

@AllArgsConstructor @RestController @RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CustomerController {
    private final ItemService itemService;
    private final CartService cartService;
    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final UserService userService;

    @GetMapping("/catalog")
    ResponseEntity<CatalogInformationResponse> getItemsByAttributes(@RequestParam Map<String, String> allRequestParams) {
        if (itemService.verifyAttributes(
                allRequestParams.remove("type"),
                allRequestParams.keySet().stream().map(UUID::fromString).collect(Collectors.toSet())
            )
        ) {
            return new ResponseEntity<>(
                    new CatalogInformationResponse(
                            itemService.findAllAppropriateItems(allRequestParams)
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cart/new") @Transactional
    ResponseEntity<CartInformationResponse> getNewCart() {
        Cart cart = cartService.createNewCart();

        return new ResponseEntity<>(
                new CartInformationResponse(
                        cart.getId(), cart.getItems()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/cart")
    ResponseEntity<CartInformationResponse> getCart(@RequestParam UUID cartId) {
        Cart cart = cartService.getCartById(cartId);

        if (cart != null) {
            return new ResponseEntity<>(
                    new CartInformationResponse(
                           cart.getId(), cart.getItems()
                    ),
                    HttpStatus.OK
            );
        }
       else
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/cart/add") @Transactional
    ResponseEntity<CartInformationResponse> addToCart(@RequestParam UUID cartId, @RequestParam UUID itemId) {
        Cart cart = cartService.getCartById(cartId);
        Item item = itemService.getItemById(itemId);

        if (cart != null && item != null) {
            cart = cartService.saveCart(cart.addItem(item));

            return new ResponseEntity<>(
                    new CartInformationResponse(
                            cart.getId(), cart.getItems()
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/cart/delete") @Transactional
    ResponseEntity<CartInformationResponse> deleteFromCart(@RequestParam UUID cartId, @RequestParam UUID itemId) {
        Cart cart = cartService.getCartById(cartId);
        Item item = itemService.getItemById(itemId);

        if (cart != null && cart.getItems().contains(item)) {
            cart = cartService.saveCart(cart.deleteItem(item));

            return new ResponseEntity<>(
                    new CartInformationResponse(
                            cart.getId(), cart.getItems()
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/cart") @Transactional
    ResponseEntity<CartInformationResponse> cleanCart(@RequestParam UUID cartId) {
        Cart cart = cartService.getCartById(cartId);

        if (cart != null) {
            cart = cartService.saveCart(cart.setItems(new ArrayList<>()));

            return new ResponseEntity<>(
                    new CartInformationResponse(
                            cart.getId(), cart.getItems()
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/order")
    ResponseEntity<OrderInformationResponse> getOrderInformation(@RequestParam UUID orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        Order order = orderService.getOrderById(orderId);

        if (order != null && order.getBuyer().equals(user)) {
            return new ResponseEntity<>(
                    new OrderInformationResponse(
                            order.getId(), order.getItems(),
                            order.getPaymentType(), order.getDelivery(),
                            order.getStatus(), order.getTimestamp()
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/order") @Transactional
    ResponseEntity<OrderInformationResponse> createOrder(@RequestBody OrderCreationRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        Cart cart = cartService.getCartById(request.getCartId());

        if (user != null && cart != null) {
            Delivery delivery = deliveryService.saveDelivery(
                    new Delivery()
                            .setType(request.getType())
                            .setStatus(request.getStatus())
                            .setDate(ZonedDateTime.now())
            );

            Order order = orderService.saveOrder(
                    new Order(
                            UUID.randomUUID(),
                            user,
                            cart.getItems(),
                            request.getPaymentType(),
                            delivery,
                            Order.Status.CREATED,
                            ZonedDateTime.now()
                    )
            );

            return new ResponseEntity<>(
                    new OrderInformationResponse(
                            order.getId(), order.getItems(),
                            order.getPaymentType(), order.getDelivery(),
                            order.getStatus(), order.getTimestamp()
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
