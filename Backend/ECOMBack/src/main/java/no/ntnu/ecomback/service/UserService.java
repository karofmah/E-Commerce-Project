package no.ntnu.ecomback.service;

import no.ntnu.ecomback.model.UpdateRequest;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.UserRepository;
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

    private UserRepository userRepository;

    //generate random and save in database every time


    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public boolean checkUserCredentials(final String email,final String password) {
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
    public User updateUser(UpdateRequest updateRequest){

        Optional<User> userByEmail = userRepository.findById(updateRequest.getEmail());

        if (userByEmail.isPresent()) {
            User _user = userByEmail.get();
            _user.setFirstName(updateRequest.getFirstName());
            _user.setLastName(updateRequest.getLastName());
            System.out.println(_user.getPassword());
            if(_user.getPassword().equals(updateRequest.getCurrentPassword())){
                _user.setPassword(updateRequest.getNewPassword());
            }
            /*else{
                System.out.println();
                _user.setPassword(updateRequest.getCurrentPassword());
            }*/

            return userRepository.save(_user);
        } else {
            return null;
        }
    }
}