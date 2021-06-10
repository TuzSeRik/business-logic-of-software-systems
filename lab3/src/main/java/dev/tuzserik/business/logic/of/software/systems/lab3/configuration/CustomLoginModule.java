package dev.tuzserik.business.logic.of.software.systems.lab3.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.jaas.JaasNameCallbackHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.security.auth.spi.LoginModule;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import java.util.Map;
import dev.tuzserik.business.logic.of.software.systems.lab3.services.UserService;

@RequiredArgsConstructor @Component
public class CustomLoginModule implements LoginModule {
    private final UserService userService;
    private Subject subject;
    private String username;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler,
                           Map<String, ?> sharedState, Map<String, ?> options) {
        this.subject = subject;

        JaasNameCallbackHandler nameCallbackHandler = new JaasNameCallbackHandler();
        NameCallback nameCallback = new NameCallback("Username:");

        nameCallbackHandler.handle(nameCallback, SecurityContextHolder.getContext().getAuthentication());

        username = nameCallback.getName();
    }

    @Override
    public boolean login() throws LoginException {
        if (userService.userExists(username)) {

            subject.getPrincipals().add(() -> userService.getUserRoleByUsername(username));

            return true;
        }
        else {
            throw new LoginException("Provided token is incorrect!");
        }
    }

    @Override
    public boolean commit() {
        return true;
    }

    @Override
    public boolean abort() {
        return true;
    }

    @Override
    public boolean logout() {
        return true;
    }
}
