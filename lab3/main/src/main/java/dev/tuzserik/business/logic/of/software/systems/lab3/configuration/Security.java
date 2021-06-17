package dev.tuzserik.business.logic.of.software.systems.lab3.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.*;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.security.auth.login.AppConfigurationEntry;
import java.util.HashMap;

@AllArgsConstructor @Configuration
public class Security extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .authorizeRequests()
                .mvcMatchers("/api/root/**").hasRole("ROOT")
                .mvcMatchers("/api/administrator/**").hasRole("ADMINISTRATOR")
                .mvcMatchers("/api/moderator/**").hasRole("MODERATOR")
                .mvcMatchers("/api/customer/catalog/**").permitAll()
                .mvcMatchers("/api/customer/cart/**").permitAll()
                .mvcMatchers("/api/customer/**").hasRole("CUSTOMER")
                .mvcMatchers("/api/user/**").permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(abstractJaasAuthenticationProvider())
                .userDetailsService(userDetailsService)
                .addFilterAfter(new JwtAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Bean
    public AbstractJaasAuthenticationProvider abstractJaasAuthenticationProvider() {
        DefaultJaasAuthenticationProvider defaultJaasAuthenticationProvider = new DefaultJaasAuthenticationProvider();

        defaultJaasAuthenticationProvider.setConfiguration(
                new InMemoryConfiguration(new AppConfigurationEntry[]{
                        new AppConfigurationEntry(
                            "dev.tuzserik.business.logic.of.software.systems.lab2.configuration.CustomLoginModule",
                            AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, new HashMap<>()
                        )
                })
        );
        defaultJaasAuthenticationProvider.setAuthorityGranters(
                new AuthorityGranter[]{new CustomAuthorityGranter()}
        );

        return defaultJaasAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
