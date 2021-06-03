package dev.tuzserik.business.logic.of.software.systems.lab2.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.UUID;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.ZonedDateTime;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.*;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.*;
import dev.tuzserik.business.logic.of.software.systems.lab2.requests.OrderCreatingRequest;
import dev.tuzserik.business.logic.of.software.systems.lab2.responses.*;

@AllArgsConstructor @RestController @RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CustomerController {
    private final CatalogService catalogService;
    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/catalog")
    ResponseEntity<CatalogInformationResponse> getItemsByAttributes(@RequestParam Map<String, String> allRequestParams) {
        if (catalogService.verifyAttributes(
                allRequestParams.remove("type"),
                allRequestParams.keySet().stream().map(UUID::fromString).collect(Collectors.toSet())
            )
        ) {
            return new ResponseEntity<>(
                    new CatalogInformationResponse(
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
    ResponseEntity<CartInformationResponse> getNewCart() {
        Cart cart = cartService.createNewCart();

        return new ResponseEntity<>(
                new CartInformationResponse(
                        cart.getId(), cart.getItemIds()
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
                           cart.getId(), cart.getItemIds()
                    ),
                    HttpStatus.OK
            );
        }
       else
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/cart/add")
    ResponseEntity<CartInformationResponse> addToCart(@RequestParam UUID cartId, @RequestParam UUID itemId) {
        Cart cart = cartService.getCartById(cartId);
        Item item = catalogService.getItemById(itemId);

        if (cart != null && item != null) {
            cart = cartService.saveCart(cart.addItemId(item.getId()));

            return new ResponseEntity<>(
                    new CartInformationResponse(
                            cart.getId(), cart.getItemIds()
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/cart/delete")
    ResponseEntity<CartInformationResponse> deleteFromCart(@RequestParam UUID cartId, @RequestParam UUID itemId) {
        Cart cart = cartService.getCartById(cartId);

        if (cart != null && cart.getItemIds().contains(itemId)) {
            cart = cartService.saveCart(cart.deleteItemId(itemId));

            return new ResponseEntity<>(
                    new CartInformationResponse(
                            cart.getId(), cart.getItemIds()
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/cart")
    ResponseEntity<CartInformationResponse> cleanCart(@RequestParam UUID cartId) {
        Cart cart = cartService.getCartById(cartId);

        if (cart != null) {
            cart = cartService.deleteCart(cart);

            return new ResponseEntity<>(
                    new CartInformationResponse(
                            cart.getId(), cart.getItemIds()
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
                            order.getId(), order.getItemsIds(),
                            order.getPaymentType(), order.getDelivery(),
                            order.getStatus(), order.getTimestamp()
                    ),
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/order")
    ResponseEntity<OrderInformationResponse> createOrder(@RequestBody OrderCreatingRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        Cart cart = cartService.getCartById(request.getCartId());

        if (user != null && cart != null) {
            Order order = orderService.saveOrder(
                    new Order(
                            UUID.randomUUID(),
                            user,
                            cart.getItemIds(),
                            request.getPaymentType(),
                            request.getDelivery(),
                            Order.Status.CREATED,
                            ZonedDateTime.now()
                    )
            );

            return new ResponseEntity<>(
                    new OrderInformationResponse(
                            order.getId(), order.getItemsIds(),
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
