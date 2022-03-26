package dev.tuzserik.business.logic.of.software.systems.lab3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.UUID;
import java.util.List;
import java.time.ZonedDateTime;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "ORDERS")
public class Order {
    @Id @GeneratedValue
    private UUID id;
    @ManyToOne
    private User buyer;
    @ManyToMany
    private List<Item> items;
    private ZonedDateTime timestamp = ZonedDateTime.now();

    private class OrderProceeding {
        private PaymentType paymentType;
        @OneToOne
        private Delivery delivery;
        private Status status;
    }

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
