package dev.tuzserik.business.logic.of.software.systems.lab2.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor @Data
public class CartStatusResponse {
    private UUID cartId;
    private Collection<UUID> items;
}
