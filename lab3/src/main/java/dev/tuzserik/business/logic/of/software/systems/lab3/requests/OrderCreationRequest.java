package dev.tuzserik.business.logic.of.software.systems.lab3.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Order;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Delivery;

@AllArgsConstructor @Data
public class OrderCreationRequest {
    private UUID cartId;
    private Order.PaymentType paymentType;
    private Delivery.Type type;
    private Delivery.Status status;
}
