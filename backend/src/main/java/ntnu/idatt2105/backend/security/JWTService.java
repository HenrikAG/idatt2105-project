package ntnu.idatt2105.backend.security;

import java.time.Duration;
import java.time.Instant;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JWTService {
    private final static Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(40);
    public final static String tempKey = "my-secret-key"; //TODO ikke ha direkte i koden.

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(JWT_TOKEN_VALIDITY.toMillis());
        Algorithm hmac512 = Algorithm.HMAC512(tempKey);

        String token = JWT.create()
            .withSubject(authentication.getName())
            .withIssuedAt(now)
            .withExpiresAt(expiry)
            .sign(hmac512);

            return token;
    }
}
