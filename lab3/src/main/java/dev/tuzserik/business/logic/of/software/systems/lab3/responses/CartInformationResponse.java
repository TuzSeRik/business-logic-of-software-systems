package dev.tuzserik.business.logic.of.software.systems.lab3.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
import java.util.List;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.Item;

@AllArgsConstructor @Data
public class CartInformationResponse {
    private UUID cartId;
    private List<Item> items;
}
