package no.ntnu.ecomback.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import no.ntnu.ecomback.model.LoginRequest;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    //generate random and save in database every time
    public static final String keyStr = "testsecrettestsecrettestsecrettestsecrettestsecret";
    private static final Duration JWT_TOKEN_VALIDITY = Duration.ofMinutes(5);

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String getToken(final LoginRequest loginRequest) throws NoSuchAlgorithmException {


        if (checkUserCredentials(loginRequest.getEmail(),loginRequest.getPassword())) {
            return generateToken(loginRequest.getEmail());
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access denied, wrong credentials....");
    }

    public String generateToken(final String email) {
        final Instant now = Instant.now();
        final Algorithm hmac512 = Algorithm.HMAC512(keyStr);

        return JWT.create()
                .withSubject(email)
                .withIssuer("idatt2105_token_issuer_app")
                .withIssuedAt(now)
                .withExpiresAt(now.plusMillis(JWT_TOKEN_VALIDITY.toMillis()))
                .sign(hmac512);
    }
    public ResponseEntity<User> registerUser(User user) {
        try {
            List<User> users = new ArrayList<>(userRepository.findAll());

            for (User u : users) {
                if (Objects.equals(u.getEmail(), user.getEmail())) {
                    System.out.println("User already exists");
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            System.out.println("User registered successfully");
            User savedUser = userRepository.save(user);
            return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error occurred while registering user: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public boolean checkUserCredentials(final String email,final String password) throws NoSuchAlgorithmException {
        for(User user : userRepository.findAll()){

            if(user.getEmail().equals(email)
            && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public Optional<User> getUser(String email){
        try {


            for (User user : userRepository.findAll()) {
                if (user.getEmail().equals(email)) {
                    return userRepository.findById(email);
                }
            }
            return Optional.empty();
        }catch(Exception e){
            System.out.println("Error occurred while : " + e.getMessage());
            return Optional.empty();
        }
    }
}