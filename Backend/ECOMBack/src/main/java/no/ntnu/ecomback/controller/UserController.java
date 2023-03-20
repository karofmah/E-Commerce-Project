package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.LoginRequest;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:5173/")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService=userService;
    }
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return userService.registerUser(user);
    }
    @PostMapping("/login/token")
    public String getToken(@RequestBody LoginRequest loginRequest) throws NoSuchAlgorithmException {
        return userService.getToken(loginRequest);
    }

    @GetMapping("/login/user")
    public Optional<User> getUser(@RequestParam String email){
        return userService.getUser(email);
    }

}
