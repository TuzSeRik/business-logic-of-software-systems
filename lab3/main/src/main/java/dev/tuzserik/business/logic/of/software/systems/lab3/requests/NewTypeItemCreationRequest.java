package dev.tuzserik.business.logic.of.software.systems.lab3.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@AllArgsConstructor @Data
public class NewTypeItemCreationRequest {
    private String itemName;
    private String typeName;
    private Map<String, String> parameters;
}
