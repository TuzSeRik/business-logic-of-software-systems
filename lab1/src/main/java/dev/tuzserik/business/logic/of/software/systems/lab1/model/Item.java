package dev.tuzserik.business.logic.of.software.systems.lab1.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "ITEMS")
public class Item {
    @Id @GeneratedValue
    private UUID id;
    private String name;
    @ManyToOne
    private Type type;
}
