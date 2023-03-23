package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.Message;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin("http://localhost:5173/")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/sendMessage")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message){
        try {
            Message _message = messageService.addMessage(message);

            return new ResponseEntity<>(_message, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAllMessages")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Message>> getItems() {
        try {
            List<Message> messages = messageService.getAllMessages();

            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{fromEmail}/{toEmail}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Message>> getMessagesByToEmailAndFromEmail(
            @PathVariable String fromEmail, @PathVariable String toEmail){

        try {
            List<Message> messages = messageService.getMessagesInBothDirections(toEmail, fromEmail);

            if (messages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{email}/contacts")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<User>> getContacts(@PathVariable String email){
        try {

            List<User> contacts = messageService.getContacts(email);

            if (contacts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
