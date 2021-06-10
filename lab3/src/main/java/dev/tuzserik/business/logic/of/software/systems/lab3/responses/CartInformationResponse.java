package dev.tuzserik.business.logic.of.software.systems.lab3.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor @Data
public class CartInformationResponse {
    private UUID cartId;
    private Collection<UUID> items;
}
