package dev.tuzserik.business.logic.of.software.systems.lab2.controllers;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.UserService;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.CheckoutService;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Delivery;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Order;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.User;
import dev.tuzserik.business.logic.of.software.systems.lab2.requests.*;
import dev.tuzserik.business.logic.of.software.systems.lab2.responses.*;

@AllArgsConstructor @RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class CheckoutController {
    private final UserService userService;
    private final CheckoutService checkoutService;

    @PostMapping("/api/checkout")
    ResponseEntity<OrderStatusResponse> createOrder(@RequestBody OrderCreatingRequest request) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(login);

        if (user != null) {
            Order order = checkoutService.createNewOrder(
                    user.getId(), request.getCartId(), request.getDelivery());
            return new ResponseEntity<>(
                new OrderStatusResponse(
                    order.getId(), order.getItems(), order.getTimestamp(),
                    order.getStatus(), order.getDelivery()),
                HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("api/delivery/update")
    ResponseEntity<DeliveryUpdateResponse> updateDelivery(
            @RequestBody DeliveryUpdateRequest request) {
                Delivery delivery = checkoutService.updateDelivery(request.getId(), request.getStatus());
                return new ResponseEntity<>(
                    new DeliveryUpdateResponse(
                        delivery.getId(), delivery.getStatus()), HttpStatus.OK
                );
    }

    @PostMapping("api/order/update")
    ResponseEntity<OrderUpdateResponse> updateOrder(
            @RequestBody OrderUpdateRequest request) {
                Order order = checkoutService.updateOrder(request.getId(), request.getStatus());
                return new ResponseEntity<>(
                    new OrderUpdateResponse(
                        order.getId(), order.getStatus()), HttpStatus.OK
                );
    }
}
