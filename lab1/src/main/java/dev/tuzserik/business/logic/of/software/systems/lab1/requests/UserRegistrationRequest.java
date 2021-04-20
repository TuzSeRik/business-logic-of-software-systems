package dev.tuzserik.business.logic.of.software.systems.lab1.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class UserRegistrationRequest {
    private String login;
    private String password;
    private String givenName;
    private String familyName;
}
