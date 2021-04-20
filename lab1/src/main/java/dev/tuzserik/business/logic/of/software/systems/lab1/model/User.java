package dev.tuzserik.business.logic.of.software.systems.lab1.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data @Entity @Table(name = "USERS")
public class User {
    @Id @GeneratedValue
    private UUID id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false) @JsonIgnore
    private String password;
    private String givenName;
    private String familyName;
}
