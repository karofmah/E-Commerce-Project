package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.UpdateUserRequest;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
    public User createUser(@RequestBody User user){
        return userService.registerUser(user);
    }


    @GetMapping("/login/user")
    public Optional<User> getUser(@RequestParam String email){
        return userService.getUser(email);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();

            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

