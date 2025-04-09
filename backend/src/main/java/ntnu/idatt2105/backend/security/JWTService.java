package ntnu.idatt2105.backend.security;

import java.time.Duration;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JWTService {
    private static final Logger logger = LoggerFactory.getLogger(JWTService.class);

    private final static Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(40);
    
    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(JWT_TOKEN_VALIDITY.toMillis());
        Algorithm hmac512 = Algorithm.HMAC512(jwtSecret);

        String role = authentication.getAuthorities().stream()
            .findFirst()
            .map(grantedAuthority -> grantedAuthority
            .getAuthority())
            .get();

        String token = JWT.create()
            .withSubject(authentication.getName())
            .withClaim("role", role)
            .withIssuedAt(now)
            .withExpiresAt(expiry)
            .sign(hmac512);

            return token;
    }

    public String validateTokenAndGetUserName(String token) {
                try {
            final Algorithm hmac512 = Algorithm.HMAC512(jwtSecret);;
            final JWTVerifier verifier = JWT.require(hmac512)
                .build();
            return verifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationEx) {
            logger.warn("token is invalid: {}", verificationEx.getMessage());
            return null;
        }
    }
}
