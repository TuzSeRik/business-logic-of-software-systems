package dev.tuzserik.business.logic.of.software.systems.lab2.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@AllArgsConstructor @Data
public class OrderUpdateRequest {
    private UUID id;
    private String status;
}
