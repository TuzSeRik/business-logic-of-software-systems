package dev.tuzserik.business.logic.of.software.systems.lab3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.UUID;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "CARTS")
public class Cart {
    @Id @GeneratedValue
    private UUID id;
    @ManyToMany
    private List<Item> items;

    public Cart addItem(Item item) {
        items.add(item);
        return this;
    }

    public Cart deleteItem(Item item) {
        items.remove(item);
        return this;
    }
}
