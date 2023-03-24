package no.ntnu.ecomback.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.EcomBackApplication;
import no.ntnu.ecomback.model.Message;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes= EcomBackApplication.class)

public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private static MessageController messageController;
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

        message1.setFromEmail("edvard@ntnu.no");
        message1.setToEmail("karo@ntnu.no");

        message2.setFromEmail("karo@ntnu.no");
        message2.setToEmail("edvard@ntnu.no");

        mockMessages.add(message1);
        mockMessages.add(message2);
        mockMessages.add(message3);

        mockMessagesToAndFrom.add(message1);
        mockMessagesToAndFrom.add(message2);

        when(messageController.getMessages()).thenReturn(new ResponseEntity<>(mockMessages, HttpStatus.OK));
        when(messageController.getMessagesByToEmailAndFromEmail(message1.getFromEmail(),message1.getToEmail()))
                .thenReturn(new ResponseEntity<>(mockMessagesToAndFrom,HttpStatus.OK));
    }

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
        //might add assert fields
    }

    @Test
    @WithMockUser(username = "USER")
    @DisplayName("Testing the endpoint for sending a valid message")
    public void sendValidMessage() throws Exception {
        Message newMessage=new Message();
        newMessage.setMessageContent("newHei");
        mockMessages.add(newMessage);
        when(messageController.sendMessage(Mockito.any(Message.class))).thenReturn(new ResponseEntity<>(mockMessages.get(0),HttpStatus.CREATED));

        String newMessageJson=objectMapper.writeValueAsString(newMessage);

        MvcResult result= mockMvc.perform(post("/api/messages/sendMessage")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newMessageJson)

                )
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("result: " + result);
        /*Assertions.assertThat(result).isNotNull();
        String userJson = result.getResponse().getContentAsString();
        assertEquals(userJson).isNotEmpty();
        Assertions.assertThat(userJson).isEqualToIgnoringCase(objectMapper.writeValueAsString(newMessage));
*/
    }

    @Test
    @WithMockUser(username = "USER")
    @DisplayName("Test the endpoint for retrieving messages based on which email they were sent from and to")
    public void getMessagesEmail() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/messages/edvard@ntnu.no/karo@ntnu.no")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<Message> actualMessages = mapper.readValue(responseString, new TypeReference<>() {
        });

        Assertions.assertEquals(mockMessagesToAndFrom.size(), actualMessages.size());
        //might add assert fields



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
