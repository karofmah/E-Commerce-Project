package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:5174/")
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
    @GetMapping("/login/token")
    public String getToken(final @RequestBody User user) throws NoSuchAlgorithmException {
        return userService.getToken(user);
    }

    @GetMapping("/login/user")
    public User getUser(@RequestParam String email){
        return userService.getUser(email)
    }

}
