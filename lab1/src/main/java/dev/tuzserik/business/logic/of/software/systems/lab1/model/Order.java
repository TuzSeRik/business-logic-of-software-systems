package dev.tuzserik.business.logic.of.software.systems.lab1.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "ORDERS")
public class Order {
    @Id @GeneratedValue
    private UUID id;
    @ManyToOne
    private User buyer;
    @ElementCollection
    private Collection<UUID> items;
    private ZonedDateTime timestamp = ZonedDateTime.now();
    private String status;
    @OneToOne
    private Delivery delivery;
}
