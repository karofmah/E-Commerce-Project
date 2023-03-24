package no.ntnu.ecomback.service;

import jakarta.transaction.Transactional;
import no.ntnu.ecomback.model.UpdateUserRequest;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.ItemRepository;
import no.ntnu.ecomback.repository.MessageRepository;
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
    private ItemRepository itemRepositoryRepository;
    private MessageRepository messageRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setItemRepositoryRepository(ItemRepository itemRepositoryRepository) {
        this.itemRepositoryRepository = itemRepositoryRepository;
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public User registerUser(User user) {

        try {
            List<User> users = new ArrayList<>(userRepository.findAll());

            for (User u : users) {
                if (Objects.equals(u.getEmail(), user.getEmail())) {
                    _logger.info("User already exists");
                    return null;
                }
            }
            _logger.info("User registered successfully");
            return userRepository.save(user);
        } catch (Exception e) {
            _logger.info("Error occurred while registering user: " + e.getMessage());
            return null;
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
                _user.setUsername(updateUserRequest.getUsername());
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

    public List<User> getAllUsers() {
        try {
            return new ArrayList<>(userRepository.findAll());
        } catch (Exception e) {
            return null;
        }
    }

    @Transactional
    public ResponseEntity<HttpStatus> deleteUser(String email) {
        try {
            itemRepositoryRepository.deleteAllBySellerEmail(email);
            messageRepository.deleteAllByFromEmailOrToEmail(email, email);
            userRepository.deleteById(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            _logger.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}