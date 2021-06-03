package dev.tuzserik.business.logic.of.software.systems.lab2.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import dev.tuzserik.business.logic.of.software.systems.lab2.model.User;

@AllArgsConstructor @Data
public class UserInformationResponse {
    private String username;
    private User.Role role;
    private String givenName;
    private String familyName;
}
