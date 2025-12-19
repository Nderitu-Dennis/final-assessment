package tech.csm.final_assmnt.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import tech.csm.final_assmnt.model.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

   // private final String SECRET_KEY = "mySecretKey12345";-postman rejected this
   private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final long EXPIRATION_TIME = 1000 * 60 * 30;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("username", user.getUsername());
        claims.put("role", user.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims extractClaims(String token) throws Exception {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new Exception("Invalid or expired token");
        }
    }
}
