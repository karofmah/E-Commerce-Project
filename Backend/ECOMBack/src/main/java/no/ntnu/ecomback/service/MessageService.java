package no.ntnu.ecomback.service;


import no.ntnu.ecomback.model.Message;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    public Message addMessage(Message message){
        try {
            return messageRepository.save(message);
        } catch (Exception e) {
            System.out.println("Error occurred while adding message: " + e.getMessage());
            return null;
        }
    }

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

    public List<Message> getMessagesInBothDirections(String email1, String email2){

        List<Message> myMessages;
        try {
            myMessages = messageRepository
                    .findByToEmailAndFromEmailOrToEmailAndFromEmailOrderByTimestamp(
                            email1, email2, email2, email1);
        } catch (Exception e) {
            System.out.println("Error getting messages: " + e.getMessage());
            myMessages = null;
        }
        return myMessages;
    }


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
