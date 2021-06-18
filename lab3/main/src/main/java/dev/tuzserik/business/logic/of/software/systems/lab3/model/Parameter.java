package dev.tuzserik.business.logic.of.software.systems.lab3.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "PARAMETERS")
public class Parameter {
    @Id @GeneratedValue
    private UUID id;
    @ManyToOne @JsonIgnore
    private Item item;
    @ManyToOne
    private Attribute attribute;
    private String value;
}
