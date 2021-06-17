package dev.tuzserik.business.logic.of.software.systems.lab3.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Item;

@AllArgsConstructor @Data
public class CatalogInformationResponse {
    private Set<Item> items;
}
