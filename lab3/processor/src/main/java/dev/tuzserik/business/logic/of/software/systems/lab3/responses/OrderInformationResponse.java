package dev.tuzserik.business.logic.of.software.systems.lab3.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
import java.util.List;
import java.time.ZonedDateTime;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Item;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Order;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Delivery;

@AllArgsConstructor @Data
public class OrderInformationResponse {
    private UUID id;
    private List<Item> items;
    private Order.PaymentType paymentType;
    private Delivery delivery;
    private Order.Status status;
    private ZonedDateTime timestamp;
}
