package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.Message;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public Message sendMessage(@RequestBody Message message){
        System.out.println(message);
        return messageService.addMessage(message);
    }


    @GetMapping("/getAllMessages")
    public ResponseEntity<List<Message>> getItems() {
        try {
            List<Message> messages = messageService.getAllMessages();

            return new ResponseEntity<>(messages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{fromEmail}/{toEmail}")
    public List<Message> getMessagesByToEmailAndFromEmail(@PathVariable String fromEmail,
                                                       @PathVariable String toEmail){
        System.out.println(fromEmail);
        System.out.println(toEmail);
        return messageService.getMessagesInBothDirections(toEmail, fromEmail);
    }


    @GetMapping("/{email}/contacts")
    public List<Optional<User>> getContacts(@PathVariable String email){
        System.out.println(email);
        return messageService.getContacts(email);
    }

}
