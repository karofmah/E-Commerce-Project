/**

 This class provides methods for managing messages between users.
 */
package no.ntnu.ecomback.service;
import no.ntnu.ecomback.model.Message;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private UserService userService;

    /**
     * Sets the user service to use for retrieving user information.
     *
     * @param userService the user service to use.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
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
     * Adds a message to the message repository.
     *
     * @param message the message to add.
     * @return the added message, or null if an error occurs.
     */
    public Message addMessage(Message message){
        try {
            return messageRepository.save(message);
        } catch (Exception e) {
            System.out.println("Error occurred while adding message: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves all messages from the message repository.
     *
     * @return a list of all messages in the message repository, or null if an error occurs.
     */
    public List<Message> getAllMessages(){
        List<Message> myMessages;
        try {
            myMessages = messageRepository.findAll();
        } catch (Exception e) {
            System.out.println("Error getting message: " + e.getMessage());
            myMessages = null;
        }
        return myMessages;
    }

    /**
     * Retrieves messages from the message repository sent between the specified users.
     *
     * @param toEmail the email address of the recipient user.
     * @param fromEmail the email address of the sender user.
     * @return a list of messages sent between the specified users, or null if an error occurs.
     */
    public List<Message> getMessagesByToAndFromEmail(String toEmail, String fromEmail){
        List<Message> myMessages;
        try {
            myMessages = messageRepository.findByToEmailAndFromEmailOrderByTimestamp(toEmail, fromEmail);
        } catch (Exception e) {
            System.out.println("Error getting messages: " + e.getMessage());
            myMessages = null;
        }
        return myMessages;
    }

    /**
     * Retrieves messages from the message repository sent between the specified users in both directions.
     *
     * @param email1 the email address of the first user.
     * @param email2 the email address of the second user.
     * @return a list of messages sent between the specified users in both directions, or null if an error occurs.
     */
    public List<Message> getMessagesInBothDirections(String email1, String email2){
        List<Message> myMessages;
        try {
            myMessages = messageRepository.findByToEmailAndFromEmailOrToEmailAndFromEmailOrderByTimestamp(email1, email2, email2, email1);
        } catch (Exception e) {
            System.out.println("Error getting messages: " + e.getMessage());
            myMessages = null;
        }
        return myMessages;
    }


    /**
     * Retrieves the contacts of a user based on their sent and received messages.
     *
     * @param email the email address of the user.
     * @return a list of Users
     */
    public List<User> getContacts(String email){

        List<User> contacts = new ArrayList<>();
        try {
            List<Message> contactMessages = new ArrayList<>();
            contactMessages.addAll(messageRepository.findByToEmail(email));
            for (Message message : contactMessages) {
                contacts.add(userService.getUser(message.getFromEmail()).get());
            }
            contactMessages.clear();
            contactMessages.addAll(messageRepository.findByFromEmail(email));
            for (Message message : contactMessages) {
                contacts.add(userService.getUser(message.getToEmail()).get());
            }
            return contacts.stream().distinct().collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error getting contacts: " + e.getMessage());
            return null;
        }
    }
}
