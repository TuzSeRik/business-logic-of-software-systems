package dev.tuzserik.business.logic.of.software.systems.lab1.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@AllArgsConstructor @Data
public class DeliveryUpdateRequest {
    private UUID id;
    private String status;
}
