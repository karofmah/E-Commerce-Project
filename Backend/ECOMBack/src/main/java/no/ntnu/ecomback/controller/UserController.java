package no.ntnu.ecomback.controller;

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


    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
             User _user=userService.registerUser(user);
            return new ResponseEntity<>(_user, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @GetMapping("/login/user")
    public Optional<User> getUser(@RequestParam String email){
        return userService.getUser(email);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public User updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(updateUserRequest);
    }

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

    @DeleteMapping("/deleteUser/{email}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("email") String email) {
        return userService.deleteUser(email);

    }


}

