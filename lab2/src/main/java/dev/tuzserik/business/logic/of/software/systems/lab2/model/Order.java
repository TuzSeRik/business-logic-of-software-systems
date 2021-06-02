package dev.tuzserik.business.logic.of.software.systems.lab2.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.UUID;
import java.util.Collection;
import java.time.ZonedDateTime;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "ORDERS")
public class Order {
    @Id @GeneratedValue
    private UUID id;
    @ManyToOne
    private User buyer;
    @ElementCollection
    private Collection<UUID> itemsIds;
    private PaymentType paymentType;
    @OneToOne
    private Delivery delivery;
    private Status status;
    private ZonedDateTime timestamp = ZonedDateTime.now();

    public enum Status {
        CREATED,
        AWAITING,
        PAYED,
        CANCELED,
        SUBMITTED
    }

    public enum PaymentType {
        ONLINE,
        ON_DELIVERY
    }
}
