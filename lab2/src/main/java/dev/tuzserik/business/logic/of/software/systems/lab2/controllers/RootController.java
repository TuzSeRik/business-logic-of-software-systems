package dev.tuzserik.business.logic.of.software.systems.lab2.controllers;

import dev.tuzserik.business.logic.of.software.systems.lab2.model.User;
import dev.tuzserik.business.logic.of.software.systems.lab2.responses.UserInformationResponse;
import dev.tuzserik.business.logic.of.software.systems.lab2.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor @RestController @RequestMapping("/api/root")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class RootController {
    private final UserService userService;

    @PostMapping("/role")
    ResponseEntity<UserInformationResponse> changeUserRole(@RequestParam UUID userId,
                                                           @RequestParam User.Role role) {
        // TODO This method should change provided user's role to provided,
        // TODO if them both are correct, and return information about user
        return null;
    }
}
