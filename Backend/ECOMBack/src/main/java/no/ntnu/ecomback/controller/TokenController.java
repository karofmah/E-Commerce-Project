package no.ntnu.ecomback.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;


import no.ntnu.ecomback.model.LoginRequest;
import no.ntnu.ecomback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping(value = "/token")
@EnableAutoConfiguration
@CrossOrigin
public class TokenController {

    // keyStr is hardcoded here for testing purpose
    // in a real scenario, it should either be stored in a database or injected from the environment
    public static final String keyStr = "testsecrettestsecrettestsecrettestsecrettestsecret";
    private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(5);


    @Autowired
    private UserService userService;

    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String generateToken(final @RequestBody LoginRequest loginRequest) throws Exception {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        if (userService.checkUserCredentials(loginRequest.getEmail(),loginRequest.getPassword())) {
            return generateToken(loginRequest.getEmail());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied, wrong credentials....");
    }

    public String generateToken(final String userId) {
        final Instant now = Instant.now();
        final Algorithm hmac512 = Algorithm.HMAC512(keyStr);;
        final JWTVerifier verifier = JWT.require(hmac512).build();
        return JWT.create()
                .withSubject(userId)
                .withIssuer("idatt2105_token_issuer_app")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
                .sign(hmac512);
    }
}