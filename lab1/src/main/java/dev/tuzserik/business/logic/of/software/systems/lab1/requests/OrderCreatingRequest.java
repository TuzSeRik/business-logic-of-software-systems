package dev.tuzserik.business.logic.of.software.systems.lab1.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.Delivery;

@AllArgsConstructor @Data
public class OrderCreatingRequest {
    private UUID cartId;
    private Delivery delivery;
}
