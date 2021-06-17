package dev.tuzserik.business.logic.of.software.systems.lab3.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class UserCreationRequest {
    private String username;
    private String password;
    private String givenName;
    private String familyName;
}
