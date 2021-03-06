package dev.tuzserik.business.logic.of.software.systems.lab3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "ATTRIBUTES")
public class Attribute {
    @Id @GeneratedValue
    private UUID id;
    @Column(unique = true)
    private String name;
    @ManyToMany(mappedBy = "attributes") @JsonIgnore @EqualsAndHashCode.Exclude
    private Set<Type> types;
}
