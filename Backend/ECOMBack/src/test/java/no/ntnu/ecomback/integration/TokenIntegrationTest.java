package no.ntnu.ecomback.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.EcomBackApplication;
import no.ntnu.ecomback.model.LoginRequest;
import no.ntnu.ecomback.model.Role;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes= EcomBackApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")

public class TokenIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    private static String asJsonString(final Object obj) throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }

    @DisplayName("Setup")
    @BeforeEach
    public void setup(){

        User user1=new User("karofm@ntnu.no","Karo","Mahmoud","karofm","pw", Role.NORMAL_USER);
        User user2=new User("karofm2@ntnu.no","Karo2","Mahmoud2","karofm2","pw2",Role.NORMAL_USER);
        User user3=new User("karofm3@ntnu.no","Karo3","Mahmoud3","karofm3","pw3",Role.NORMAL_USER);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
    @DisplayName("Teardown of userRepository table")
    @AfterEach
    public void teardown(){
        userRepository.deleteAll();
    }
    @Nested
    class TestGetToken{
        @Test
        @DisplayName("Tests whether token is retrieved for authorized user")
        public void getTokenAuthorized() throws Exception {

            LoginRequest loginRequest=new LoginRequest("karofm@ntnu.no","pw");

            MvcResult result=mockMvc.perform(post("/token")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(loginRequest)))
                    .andReturn();

            String responseString = result.getResponse().getContentAsString();

            System.out.println(responseString);
            Assertions.assertNotNull(responseString);
            Assertions.assertEquals(219,responseString.length());
        }

        @Test
        @DisplayName("Tests if token is not retrieved for unauthorized user")
        public void getTokenUnauthorized() throws Exception {
            LoginRequest loginRequest=new LoginRequest("karofm4@ntnu.no","pw4");
            mockMvc.perform(post("/token")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(loginRequest)))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        }

    }
}
