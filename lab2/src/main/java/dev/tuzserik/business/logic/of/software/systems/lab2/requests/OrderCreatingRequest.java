package dev.tuzserik.business.logic.of.software.systems.lab2.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Order;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Delivery;

@AllArgsConstructor @Data
public class OrderCreatingRequest {
    private UUID cartId;
    private Order.PaymentType paymentType;
    private Delivery delivery;
}
