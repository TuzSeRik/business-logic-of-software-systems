package dev.tuzserik.business.logic.of.software.systems.lab3.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.User;

@AllArgsConstructor @Data
public class UserInformationResponse {
    private String username;
    private User.Role role;
    private String givenName;
    private String familyName;

    public UserInformationResponse(User user) {
        this.username = user.getUsername();
        this.role = user.getRole();
        this.givenName = user.getGivenName();
        this.familyName = user.getFamilyName();
    }
}
