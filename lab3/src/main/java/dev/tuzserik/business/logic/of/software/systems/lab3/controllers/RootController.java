package dev.tuzserik.business.logic.of.software.systems.lab3.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import dev.tuzserik.business.logic.of.software.systems.lab3.services.UserService;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.User;
import dev.tuzserik.business.logic.of.software.systems.lab3.responses.UserInformationResponse;

@AllArgsConstructor @RestController @RequestMapping("/api/root")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RootController {
    private final UserService userService;

    @PostMapping("/role")
    ResponseEntity<UserInformationResponse> changeUserRole(@RequestParam String username, @RequestParam User.Role role) {
        User user = userService.saveUser(userService.findUserByUsername(username).setRole(role));

        return new ResponseEntity<>(
                new UserInformationResponse(
                        user.getUsername(), user.getRole(),
                        user.getGivenName(), user.getFamilyName()
                ),
                HttpStatus.OK
        );
    }
}
