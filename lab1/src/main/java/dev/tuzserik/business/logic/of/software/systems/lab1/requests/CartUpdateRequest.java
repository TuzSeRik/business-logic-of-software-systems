package dev.tuzserik.business.logic.of.software.systems.lab1.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor @Data
public class CartUpdateRequest {
    private UUID cartId;
    private Collection<UUID> items;
}
