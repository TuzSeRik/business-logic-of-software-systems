package dev.tuzserik.business.logic.of.software.systems.lab3.model;

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
    private String username;
    @Column(nullable = false) @JsonIgnore
    private String password;
    @Column(nullable = false)
    private Role role;
    private String givenName = "Default";
    private String familyName = "User";

    public enum Role {
        ROLE_USER,
        ROLE_CUSTOMER,
        ROLE_ADMINISTRATOR,
        ROLE_MODERATOR,
        ROLE_ROOT
    }
}
