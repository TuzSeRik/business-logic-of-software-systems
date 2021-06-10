package dev.tuzserik.business.logic.of.software.systems.lab3.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
import java.util.Collection;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Parameter;

@AllArgsConstructor @Data
public class ItemInformationResponse {
    private UUID id;
    private String name;
    private Collection<Parameter> parameters;
}
