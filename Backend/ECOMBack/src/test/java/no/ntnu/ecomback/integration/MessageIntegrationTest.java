package no.ntnu.ecomback.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.EcomBackApplication;
import no.ntnu.ecomback.controller.MessageController;
import no.ntnu.ecomback.model.Message;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.MessageRepository;
import no.ntnu.ecomback.service.MessageService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes= EcomBackApplication.class)
@TestPropertySource(locations = "classpath:application-karo.properties")

public class MessageIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MessageController messageController;

    @Autowired
    MessageService messageService;

    @Autowired
    MessageRepository messageRepository;


    @BeforeEach
    @DisplayName("Setting up mock data for tests")
    public void setup() {

        Message message1 = new Message();
        Message message2 = new Message();

        message1.setMessageContent("hei1");
        message2.setMessageContent("hei2");

        message1.setFromEmail("edvard@ntnu.no");
        message1.setToEmail("karo@ntnu.no");

        message2.setFromEmail("karo@ntnu.no");
        message2.setToEmail("edvard@ntnu.no");

        messageRepository.save(message1);
        messageRepository.save(message2);

    }

    @AfterEach
    public void teardown() {
        messageRepository.deleteAll();
    }

    @Nested
    class TestGetMessages {
        @Test
        @WithMockUser(username = "USER")
        @DisplayName("Testing the endpoint for retrieving all messages as a valid user")
        public void getMessages() throws Exception {

            MvcResult result = mockMvc.perform(get("/api/messages/getAllMessages")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String responseString = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            List<Message> actualMessages = mapper.readValue(responseString, new TypeReference<>() {
            });

            Assertions.assertEquals(messageRepository.findAll().size(), actualMessages.size());
        }

    }

    @Nested
    class TestSendMessage {
        @Test
        @WithMockUser(username = "USER")
        @DisplayName("Testing the endpoint for sending a valid message")
        public void sendValidMessage() throws Exception {
            Message newMessage = new Message();
            newMessage.setMessageContent("hei3");


            String newMessageJson = objectMapper.writeValueAsString(newMessage);

            mockMvc.perform(post("/api/messages/sendMessage")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newMessageJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated());


            System.out.println(messageRepository.findAll());
            Message retrievedMessage = messageRepository.findAll()
                    .get(messageRepository.findAll().size() - 1);

            Assertions.assertNotEquals(0, retrievedMessage.getMessageId());
            Assertions.assertEquals(newMessage.getMessageContent(), retrievedMessage.getMessageContent());


        }

        /*
        @Test
        @WithMockUser(username = "USER")
        @DisplayName("Testing the endpoint for sending a message with invalid content")
        public void sendInvalidMessage() throws Exception {

            String newMessageJson = objectMapper.writeValueAsString(new InvalidMessage());


            mockMvc.perform(post("/api/messages/sendMessage")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newMessageJson)
                    )
                    .andExpect(status().isInternalServerError());
        }*/

    }


    /*
    @Nested
    class TestGetMessagesEmail{

        @Test
        @WithMockUser(username = "USER")
        @DisplayName("Test the endpoint for retrieving messages based on which email they were sent from and to")
        public void getMessagesEmail() throws Exception {

            MvcResult result = mockMvc.perform(get("/api/messages/'karo@ntnu.no'/'edvard@ntnu.no'")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String responseString = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            List<Message> actualMessages = mapper.readValue(responseString, new TypeReference<>() {
            });

            Assertions.assertEquals(mockMessagesToAndFrom.size(), actualMessages.size());

            System.out.println(actualMessages);
            System.out.println(mockMessagesToAndFrom);

        }
        @Test
        @WithMockUser(username = "USER")
        @DisplayName("Test the endpoint for retrieving messages based on which email they were sent from and to")
        public void getEmptyMessagesEmail() throws Exception {
            mockMessagesToAndFrom.clear();
            when(messageController.getMessagesByToEmailAndFromEmail(Mockito.any(String.class),Mockito.any(String.class)))
                    .thenReturn(new ResponseEntity<>(mockMessagesToAndFrom,HttpStatus.NO_CONTENT));
            MvcResult result = mockMvc.perform(get("/api/messages/karo@ntnu.no/edvard@ntnu.no")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andReturn();

            String responseString = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            List<Message> actualMessages = mapper.readValue(responseString, new TypeReference<>() {
            });

            Assertions.assertEquals(mockMessagesToAndFrom.size(), actualMessages.size());
            //might add assert fields
            System.out.println(actualMessages);
            System.out.println(mockMessagesToAndFrom);

        }*
    }*/
}