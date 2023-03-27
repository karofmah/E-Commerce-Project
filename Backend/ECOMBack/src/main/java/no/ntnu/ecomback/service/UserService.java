/**

 This class provides methods for managing user accounts.
 */
package no.ntnu.ecomback.service;
import jakarta.transaction.Transactional;
import no.ntnu.ecomback.model.Role;
import no.ntnu.ecomback.model.UpdateUserRequest;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.BookmarkRepository;
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
    private BookmarkRepository bookmarkRepository;

    /**
     * Sets the user repository to use for database access.
     *
     * @param userRepository the user repository to use.
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Sets the item repository to use for database access.
     *
     * @param itemRepositoryRepository the item repository to use.
     */
    @Autowired
    public void setItemRepositoryRepository(ItemRepository itemRepositoryRepository) {
        this.itemRepositoryRepository = itemRepositoryRepository;
    }

    /**
     * Sets the message repository to use for database access.
     *
     * @param messageRepository the message repository to use.
     */
    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Sets the bookmark repository to use for database access.
     *
     * @param bookmarkRepository the bookmark repository to use.
     */
    @Autowired
    public void setBookmarkRepository(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    /**
     * Registers a new user account if the email is not already in use.
     *
     * @param user the user account to register.
     * @return the registered user account.
     */
    public User registerUser(User user) {
        try {
            List<User> users = new ArrayList<>(userRepository.findAll());

            for (User u : users) {
                if (Objects.equals(u.getEmail(), user.getEmail())) {
                    _logger.info("User already exists");
                    return null;
                }
            }

            user.setRole(Role.NORMAL_USER);

            _logger.info("User registered successfully");
            return userRepository.save(user);
        } catch (Exception e) {
            _logger.info("Error occurred while registering user: " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if the specified user credentials are valid.
     *
     * @param email    the email of the user.
     * @param password the password of the user.
     * @return true if the user credentials are valid, false otherwise.
     */
    public boolean checkUserCredentials(final String email, final String password) {

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

    /**
     * This method retrieves a user with a specified email address from the database.
     *
     * @param email the email address of the user to retrieve.
     * @return an Optional object containing the user with the specified email address, or an empty Optional object if the user does not exist in the database.
     */
    public Optional<User> getUser(String email) {
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

    /**
     * This method updates a user's information based on the provided UpdateUserRequest object.
     *
     * @param updateUserRequest an object containing the updated user information.
     * @return the updated User object, or null if the user cannot be updated.
     */
    public User updateUser(UpdateUserRequest updateUserRequest) {

        try {
            Optional<User> userByEmail = userRepository.findById(updateUserRequest.getEmail());

            if (userByEmail.isPresent()) {
                User _user = userByEmail.get();
                _user.setFirstName(updateUserRequest.getFirstName());
                _user.setLastName(updateUserRequest.getLastName());
                _user.setUsername(updateUserRequest.getUsername());
                System.out.println(_user.getPassword());
                if (_user.getPassword().equals(updateUserRequest.getCurrentPassword())) {
                    _user.setPassword(updateUserRequest.getNewPassword());
                }
                return userRepository.save(_user);
            }

        } catch (Exception e) {
            _logger.warn(e.getMessage());
            return null;
        }
        _logger.warn("User doesnt exist");
        return null;

    }

    /**
     * This method retrieves all users from the database.
     *
     * @return a List object containing all users in the database, or null if an error occurs while retrieving the users.
     */
    public List<User> getAllUsers() {
        try {
            return new ArrayList<>(userRepository.findAll());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method deletes a user with the specified email address from the database, along with all associated bookmarks, items, and messages.
     *
     * @param email the email address of the user to delete.
     * @return a ResponseEntity object indicating whether the deletion was successful or not.
     */
    @Transactional
    public ResponseEntity<HttpStatus> deleteUser(String email) {
        try {
            bookmarkRepository.deleteBookmarkByUserEmail(email);
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