package dev.tuzserik.business.logic.of.software.systems.lab3.controllers;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import dev.tuzserik.business.logic.of.software.systems.lab3.utils.Jwt;
import dev.tuzserik.business.logic.of.software.systems.lab3.services.UserService;
import dev.tuzserik.business.logic.of.software.systems.lab3.model.User;
import dev.tuzserik.business.logic.of.software.systems.lab3.requests.UserCreationRequest;
import dev.tuzserik.business.logic.of.software.systems.lab3.responses.UserInformationResponse;

@AllArgsConstructor @RestController @RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth")
    ResponseEntity<UserInformationResponse> authoriseUser(@RequestParam String username,
                                                          @RequestParam String password) {
        if (userService.verifyUser(username, password)) {
            User user = userService.findUserByUsername(username);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set(HttpHeaders.AUTHORIZATION, Jwt.encodeUsernameAndRole(
                    user.getUsername(),
                    user.getRole().name())
            );

            return new ResponseEntity<>(
                    new UserInformationResponse(user.getUsername(), user.getRole(),
                                                user.getGivenName(), user.getFamilyName()),
                    httpHeaders,
                    HttpStatus.OK
            );
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("")
    ResponseEntity<UserInformationResponse> getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);

        if (user != null) {
            return new ResponseEntity<>(new UserInformationResponse(
                    user.getUsername(), user.getRole(), user.getGivenName(), user.getFamilyName()),
                    HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("") @Transactional
    ResponseEntity<UserInformationResponse> addUser(@RequestBody UserCreationRequest request) {
        User user = userService.saveUserForAFirstTime(
                new User()
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setRole(User.Role.ROLE_CUSTOMER)
                .setGivenName(request.getGivenName())
                .setFamilyName(request.getFamilyName())
        );

        return new ResponseEntity<>(new UserInformationResponse(
                user.getUsername(), user.getRole(), user.getGivenName(), user.getFamilyName()),
                HttpStatus.OK);
    }
}
