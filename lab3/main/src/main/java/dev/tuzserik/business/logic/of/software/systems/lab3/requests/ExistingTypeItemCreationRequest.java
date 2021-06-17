package dev.tuzserik.business.logic.of.software.systems.lab3.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
import java.util.Map;

@AllArgsConstructor @Data
public class ExistingTypeItemCreationRequest {
    private String name;
    private UUID typeId;
    private Map<UUID, String> parameters;
}
