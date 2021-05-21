package dev.tuzserik.business.logic.of.software.systems.lab2.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab2.repositories.CartRepository;
import dev.tuzserik.business.logic.of.software.systems.lab2.repositories.DeliveryRepository;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Delivery;
import dev.tuzserik.business.logic.of.software.systems.lab2.repositories.OrderRepository;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Order;
import dev.tuzserik.business.logic.of.software.systems.lab2.repositories.UserRepository;

@AllArgsConstructor @Service
public class OrderService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final DeliveryRepository deliveryRepository;

    public Order createNewOrder(UUID id, UUID cartId, Delivery delivery) {
        delivery = deliveryRepository.save(delivery);
        return orderRepository.save(
            new Order(
                UUID.randomUUID(),
                userRepository.getOne(id),
                new ArrayList<>(cartRepository.getOne(cartId).getItemIds()),
                ZonedDateTime.now(),
                "Created",
                delivery
            )
        );
    }

    public Delivery updateDelivery(UUID id, String status) {
        return deliveryRepository.save(deliveryRepository.getOne(id).setStatus(status));
    }

    public Order updateOrder(UUID id, String status) {
        return orderRepository.save(orderRepository.getOne(id).setStatus(status));
    }
}
