package dev.tuzserik.business.logic.of.software.systems.lab2.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Collection;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Type;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.Parameter;

@AllArgsConstructor @Data
public class CatalogQueryingRequest {
    private Type type;
    private Collection<Parameter> parameters;
}
