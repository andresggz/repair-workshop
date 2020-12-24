package co.edu.udea.ingweb.repairworkshop.config.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    private static final String BEARER = "Bearer ";
    private static final String USER_EMAIL_CLAIM = "userEmail";
    private static final String USER_ID_CLAIM = "userId";
    private static final String ROLE_CLAIM = "role";

    private final String secret = "SUPER_SECRET";
    private final String issuer = "ing-web-udea";
    private final int expire = 3600;

 /*   @Autowired
    public JwtService(@Value("miw.jwt.secret") String secret, @Value("miw.jwt.issuer") String issuer,
                      @Value("${miw.jwt.expire}") int expire) {
        this.secret = secret;
        this.issuer = issuer;
        this.expire = expire;
    }*/

    public String extractToken(String bearer) {
        if (bearer != null && bearer.startsWith(BEARER) && 3 == bearer.split("\\.").length) {
            return bearer.substring(BEARER.length());
        } else {
            return "";
        }
    }

    public String createToken(String userEmail, Long userId, String role) {
        return JWT.create()
                .withIssuer(this.issuer)
                .withIssuedAt(new Date())
                .withNotBefore(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.expire * 1000))
                .withClaim(USER_EMAIL_CLAIM, userEmail)
                .withClaim(USER_ID_CLAIM, userId)
                .withClaim(ROLE_CLAIM, role)
                .sign(Algorithm.HMAC256(this.secret));
    }

    public String userEmail(String authorization) {
        return this.verify(authorization)
                .map(jwt -> jwt.getClaim(USER_EMAIL_CLAIM).asString())
                .orElse("");
    }

    public Long userId(String authorization) {
        return this.verify(authorization)
                .map(jwt -> jwt.getClaim(USER_ID_CLAIM).asLong())
                .orElse(-1L);
    }

    public String role(String authorization) {
        return this.verify(authorization)
                .map(jwt -> jwt.getClaim(ROLE_CLAIM).asString())
                .orElse("");
    }

    private Optional<DecodedJWT> verify(String token) {
        try {
            return Optional.of(JWT.require(Algorithm.HMAC256(this.secret))
                    .withIssuer(this.issuer).build()
                    .verify(token));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

}