package dev.tuzserik.business.logic.of.software.systems.lab2.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor @Data
public class CatalogInformationResponse {
    private Collection<UUID> items;
}
