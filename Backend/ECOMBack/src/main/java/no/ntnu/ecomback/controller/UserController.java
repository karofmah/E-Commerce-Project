package no.ntnu.ecomback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import no.ntnu.ecomback.model.UpdateUserRequest;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:5173/")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService=userService;
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody(description = "User object to be created") User user){
        try{
             User _user=userService.registerUser(user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @Operation(summary = "Get a user", description = "Used for login")
    @GetMapping("/login/user")
    public Optional<User> getUser(
            @Parameter(description = "The email of the user to get")
            @RequestParam String email){
        return userService.getUser(email);
    }
    @Operation(summary = "Update a user")
    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public User updateUser(
            @RequestBody(description = "The user object that will replace the current user")
            UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

    @Operation(summary = "Get all users")
    @GetMapping("/getAllUsers")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete user by their email")
    @DeleteMapping("/deleteUser/{email}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteUser(
            @Parameter(description = "Email of the user to delete")
            @PathVariable("email") String email) {
        try {
            userService.deleteUser(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}

