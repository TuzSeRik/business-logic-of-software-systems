package dev.tuzserik.business.logic.of.software.systems.lab2.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "TYPES")
public class Type {
    @Id @GeneratedValue
    private UUID id;
    @ManyToMany
    private Collection<Attribute> attributes = new HashSet<>();
}
