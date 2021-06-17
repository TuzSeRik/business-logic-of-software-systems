package dev.tuzserik.business.logic.of.software.systems.lab3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.UUID;
import java.time.ZonedDateTime;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "DELIVERIES")
public class Delivery {
    @Id @GeneratedValue
    private UUID id;
    private Type type;
    private Status status;
    private ZonedDateTime date = ZonedDateTime.now();

    public enum Type {
        COURIER,
        MAIL,
        PICKUP
    }

    public enum Status {
        AWAITING,
        CANCELED,
        SUBMITTED
    }
}
