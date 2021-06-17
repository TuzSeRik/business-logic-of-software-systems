package dev.tuzserik.business.logic.of.software.systems.lab3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "PARAMETERS")
public class Parameter {
    @Id @GeneratedValue
    private UUID id;
    @ManyToOne
    private Item item;
    @ManyToOne
    private Attribute attribute;
    private String value;
}
