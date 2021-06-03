package dev.tuzserik.business.logic.of.software.systems.lab2.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
import java.time.ZonedDateTime;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Delivery;

@AllArgsConstructor @Data
public class DeliveryInformationResponse {
    private UUID id;
    private Delivery.Type type;
    private Delivery.Status status;
    private ZonedDateTime timestamp;
}
