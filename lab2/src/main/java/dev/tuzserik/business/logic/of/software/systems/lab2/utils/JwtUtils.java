package dev.tuzserik.business.logic.of.software.systems.lab2.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.data.util.Pair;
import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {
    private static final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String encodeUsernameAndRole(String username, String role) {
        return Jwts
                   .builder()
                   .setSubject(username)
                   .claim("role", role)
                   .setExpiration(new Date(System.currentTimeMillis() + 864000000))
                   .signWith(jwtSecret)
                   .compact()
               ;
    }

    public static Pair<String, Object> decodeUsernameAndRole(String jwtToken) {
        Claims claims = Jwts
                            .parserBuilder()
                            .setSigningKey(jwtSecret)
                            .build()
                            .parseClaimsJws(jwtToken)
                            .getBody()
                        ;


        return Pair.of(claims.getSubject(), claims.get("role"));
    }
}
