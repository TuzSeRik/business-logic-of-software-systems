package dev.tuzserik.business.logic.of.software.systems.lab3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "TYPES")
public class Type {
    @Id @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String name;
    @ManyToMany
    private Set<Attribute> attributes;
}
