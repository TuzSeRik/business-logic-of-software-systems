package dev.tuzserik.business.logic.of.software.systems.lab2.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
import java.util.Collection;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Parameter;

@AllArgsConstructor @Data
public class ItemInformationResponse {
    private UUID id;
    private String name;
    private Collection<Parameter> parameters;
}
