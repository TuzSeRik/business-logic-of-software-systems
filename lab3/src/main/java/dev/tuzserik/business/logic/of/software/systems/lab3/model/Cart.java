package dev.tuzserik.business.logic.of.software.systems.lab3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.*;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "CARTS")
public class Cart {
    @Id @GeneratedValue
    private UUID id;
    @ElementCollection
    private Collection<UUID> itemIds = new ArrayList<>();

    public Cart addItemId(UUID itemId) {
        itemIds.add(itemId);
        return this;
    }

    public Cart deleteItemId(UUID itemId) {
        itemIds.remove(itemId);
        return this;
    }
}
