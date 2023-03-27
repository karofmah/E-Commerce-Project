/**

 This class provides methods for managing JWT tokens used for user authentication.
 */
package no.ntnu.ecomback.service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import no.ntnu.ecomback.model.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {
    private static final Logger _logger = LoggerFactory.getLogger(TokenService.class);
    // Replace with a randomly generated key for production use.
    public static final String keyStr = "testsecrettestsecrettestsecrettestsecrettestsecret";
    private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(5);

    private UserService userService;

    /**
     * Sets the user service to use for user authentication.
     *
     * @param userService the user service to use.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Generates a JWT token for the specified login request if the user credentials are valid.
     *
     * @param loginRequest the login request containing user credentials.
     * @return the generated JWT token.
     * @throws ResponseStatusException if the user credentials are invalid.
     */
    public String getToken(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        if (userService.checkUserCredentials(email, password)) {
            _logger.info("Token is generating");
            return generateToken(email);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied, wrong credentials....");
        }
    }

    /**
     * Generates a JWT token for the specified user ID.
     *
     * @param userId the user ID to generate the token for.
     * @return the generated JWT token.
     */
    public String generateToken(final String userId) {
        final Instant now = Instant.now();
        final Algorithm hmac512 = Algorithm.HMAC512(keyStr);
        final JWTVerifier verifier = JWT.require(hmac512).build();
        return JWT.create()
            .withSubject(userId)
            .withIssuer("ecom")
            .withIssuedAt(now)
            .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
            .sign(hmac512);
    }
}