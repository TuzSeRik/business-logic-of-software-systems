package dev.tuzserik.business.logic.of.software.systems.lab1.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor @Data
public class UserInformationResponse {
    private String login;
    private String givenName;
    private String familyName;
}
