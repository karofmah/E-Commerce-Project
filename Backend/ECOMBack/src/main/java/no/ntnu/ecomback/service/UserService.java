package no.ntnu.ecomback.service;

import no.ntnu.ecomback.model.UpdateUserRequest;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger _logger =
            LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<User> registerUser(User user) {

        try {
            List<User> users = new ArrayList<>(userRepository.findAll());

            for (User u : users) {
                if (Objects.equals(u.getEmail(), user.getEmail())) {
                    _logger.info("User already exists");
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            _logger.info("User registered successfully");
            User savedUser = userRepository.save(user);
            return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
        } catch (Exception e) {
            _logger.info("Error occurred while registering user: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean checkUserCredentials(final String email,final String password) {

        _logger.info("Attempting to check user credentials for email: " + email);

        boolean isAuthenticated = userRepository.findAll()
                .stream()
                .anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));

        if (isAuthenticated) {
            _logger.info("User credentials verified for email: " + email);
        } else {
            _logger.info("Invalid user credentials for email: " + email);
        }

        return isAuthenticated;

    }
    public Optional<User> getUser(String email){
        Optional<User> userOptional = userRepository.findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .or(Optional::empty);

        if (userOptional.isPresent()) {
            _logger.info("Successfully retrieved user with email: " + email);
        } else {
            _logger.info("Could not find user with email: " + email);
        }

        return userOptional;
    }
    public User updateUser(UpdateUserRequest updateUserRequest){

        try{

            Optional<User> userByEmail = userRepository.findById(updateUserRequest.getEmail());

            if (userByEmail.isPresent()) {
                User _user = userByEmail.get();
                _user.setFirstName(updateUserRequest.getFirstName());
                _user.setLastName(updateUserRequest.getLastName());
                System.out.println(_user.getPassword());
                if(_user.getPassword().equals(updateUserRequest.getCurrentPassword())){
                    _user.setPassword(updateUserRequest.getNewPassword());
                }

                return userRepository.save(_user);
            }
        }catch (Exception e){
            _logger.warn(e.getMessage());
            return null;
        }
        _logger.warn("User doesnt exist");
        return null;

    }
}