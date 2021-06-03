package dev.tuzserik.business.logic.of.software.systems.lab2.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Order;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Delivery;

@AllArgsConstructor @Data
public class OrderInformationResponse {
    private UUID id;
    private Collection<UUID> items;
    private Order.PaymentType paymentType;
    private Delivery delivery;
    private Order.Status status;
    private ZonedDateTime timestamp;
}
