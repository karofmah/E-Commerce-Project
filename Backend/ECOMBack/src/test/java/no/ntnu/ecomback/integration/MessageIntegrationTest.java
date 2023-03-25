package no.ntnu.ecomback.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.EcomBackApplication;
import no.ntnu.ecomback.controller.MessageController;
import no.ntnu.ecomback.model.Message;
import no.ntnu.ecomback.repository.MessageRepository;
import no.ntnu.ecomback.service.MessageService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes= EcomBackApplication.class)

public class MessageIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MessageController messageController;

    @Autowired
    MessageService messageService;

    @MockBean
    MessageRepository messageRepository;
    List <Message> mockMessagesToAndFrom=new ArrayList<>();
    List <Message> mockMessages=new ArrayList<>();

    @BeforeEach
    @DisplayName("Setting up mock data for tests")
    public void setup() {

        Message message1=new Message();
        Message message2=new Message();
        Message message3=new Message();

        message1.setMessageContent("hei1");
        message2.setMessageContent("hei2");
        message3.setMessageContent("hei3");

        message1.setMessageId(1);
        message2.setMessageId(2);

        message1.setFromEmail("edvard@ntnu.no");
        message1.setToEmail("karo@ntnu.no");

        message2.setFromEmail("karo@ntnu.no");
        message2.setToEmail("edvard@ntnu.no");

        mockMessages.add(message1);
        mockMessages.add(message2);
        mockMessages.add(message3);

        mockMessagesToAndFrom.add(message1);
        mockMessagesToAndFrom.add(message2);

        when(messageRepository.findAll()).thenReturn(mockMessages);
        when(messageRepository.findByToEmailAndFromEmailOrToEmailAndFromEmailOrderByTimestamp
                ("karofm@ntnu.no","edvard@ntnu.no","edvard@ntnu.no",
                        "karo@ntnu.no")).thenReturn(mockMessagesToAndFrom);

    }

    @Nested
    class TestGetMessages{
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
            Assertions.assertEquals(mockMessages.size(), actualMessages.size());

            System.out.println(actualMessages);
        }
        @Test
        @DisplayName("Testing the endpoint for retrieving all messages as an invalid user")
        public void getMessagesInvalid() throws Exception {

            mockMvc.perform(get("/api/messages/getAllMessages")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(403))
                    .andReturn();
        }

    }
    @Test
    @WithMockUser(username = "USER")
    @DisplayName("Testing the endpoint for sending a valid message")
    public void sendValidMessage() throws Exception {
        Message newMessage=new Message();
        newMessage.setMessageContent("newHei");
        mockMessages.add(newMessage);
        when(messageRepository.save(Mockito.any(Message.class))).thenReturn(newMessage);

        String newMessageJson=objectMapper.writeValueAsString(newMessage);

        MvcResult result= mockMvc.perform(post("/api/messages/sendMessage")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newMessageJson)

                )
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertNotNull(result);
        String messageJson = result.getResponse().getContentAsString();
        Assertions.assertEquals(messageJson,objectMapper.writeValueAsString(newMessage));
        System.out.println(messageJson);
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