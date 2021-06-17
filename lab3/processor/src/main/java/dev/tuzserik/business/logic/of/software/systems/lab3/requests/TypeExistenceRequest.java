package dev.tuzserik.business.logic.of.software.systems.lab3.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@AllArgsConstructor @Data
public class TypeExistenceRequest {
    private String typeName;
    private Set<String> attributesNames;
}
