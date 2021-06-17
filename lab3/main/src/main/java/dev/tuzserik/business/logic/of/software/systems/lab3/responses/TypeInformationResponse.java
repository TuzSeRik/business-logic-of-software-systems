package dev.tuzserik.business.logic.of.software.systems.lab3.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
import java.util.Set;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Attribute;

@AllArgsConstructor @Data
public class TypeInformationResponse {
    private UUID id;
    private String name;
    private Set<Attribute> attributes;
}
