package dev.tuzserik.business.logic.of.software.systems.lab2.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class ParameterCreationRequest {
    private String name;
    private String value;
}
