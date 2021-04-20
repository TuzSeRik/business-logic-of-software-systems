package dev.tuzserik.business.logic.of.software.systems.lab1.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor @Data
public class CatalogListResponse {
    private Collection<UUID> items;
}
