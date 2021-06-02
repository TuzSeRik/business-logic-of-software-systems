package dev.tuzserik.business.logic.of.software.systems.lab2.configuration;

import io.jsonwebtoken.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import dev.tuzserik.business.logic.of.software.systems.lab2.utils.Jwt;

public class JwtAuthorisationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws IOException, ServletException {
        try {
            String authenticationHeader = request.getHeader("Authorization");

            if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                Jwt.decodeUsernameAndRole(authenticationHeader.replace("Bearer ", "")).getFirst(),
                                null,
                                Collections.singleton(
                                        new SimpleGrantedAuthority(
                                                (String) Jwt
                                                        .decodeUsernameAndRole(
                                                                authenticationHeader
                                                                        .replace("Bearer ", "")
                                                        ).getSecond()
                                        )
                                )
                        )
                );
            }
            else {
                SecurityContextHolder.clearContext();
            }

            filterChain.doFilter(request, response);
        }
        catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }
}
