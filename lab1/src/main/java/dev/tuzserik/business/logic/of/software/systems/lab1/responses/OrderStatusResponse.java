package dev.tuzserik.business.logic.of.software.systems.lab1.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab1.model.Delivery;

@AllArgsConstructor @Data
public class OrderStatusResponse {
    private UUID id;
    private Collection<UUID> items;
    private ZonedDateTime timestamp;
    private String status;
    private Delivery delivery;
}
