package dev.tuzserik.business.logic.of.software.systems.lab3.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab3.repositories.OrderRepository;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Order;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Delivery;

@AllArgsConstructor @Service
public class OrderService {
    private final OrderRepository orderRepository;

    public Order getOrderById(UUID id){
        return orderRepository.getOne(id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public boolean submitOrderPaymentWithDelivery(Delivery delivery) {
        orderRepository.save(orderRepository.findOrderByDelivery(delivery).setStatus(Order.Status.PAYED));
        return true;
    }
}
