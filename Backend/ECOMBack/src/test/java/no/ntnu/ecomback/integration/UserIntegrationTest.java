package no.ntnu.ecomback.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.EcomBackApplication;
import no.ntnu.ecomback.controller.UserController;
import no.ntnu.ecomback.model.Role;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.UserRepository;
import no.ntnu.ecomback.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes= EcomBackApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")

public class UserIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserController userController;
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    @DisplayName("Setting up mock data for tests")
    public void setup() {


        User user1=new User("karofm@ntnu.no","Karo","Mahmoud","karofm","pw",Role.NORMAL_USER);
        User user2=new User("karofm2@ntnu.no","Karo2","Mahmoud2","karofm2","pw2",Role.NORMAL_USER);
        User user3=new User("karofm3@ntnu.no","Karo3","Mahmoud3","karofm3","pw3",Role.NORMAL_USER);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }
    @AfterEach
    public void teardown(){
       userRepository.deleteAll();
    }

    @Nested
    class TestGetUsers{

        @Test
        @WithMockUser(username = "USER")
        @DisplayName("Testing the endpoint for retrieving all users")
        public void getUsers() throws Exception {

            MvcResult result = mockMvc.perform(get("/api/users/getAllUsers")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

            String responseString = result.getResponse().getContentAsString();
            ObjectMapper mapper = new ObjectMapper();
            List<User> actualUsers = mapper.readValue(responseString, new TypeReference<>() {
            });

            Assertions.assertEquals(userRepository.findAll().size(),actualUsers.size());
        }

    }

    @Test
    @DisplayName("Testing the endpoint for registering a new user")
    public void registerNewUser() throws Exception {
        User newUser=new User("karofm6@ntnu.no","Karo2","Mahmoud2","karofm2","pw2",Role.NORMAL_USER);


        String newUserJson=objectMapper.writeValueAsString(newUser);

        mockMvc.perform(post("/api/users/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Optional<User> userOptional = userRepository.findById(newUser.getEmail());
        Assertions.assertTrue(userOptional.isPresent());
        User retrievedUser = userOptional.get();
        Assertions.assertEquals(newUser.getEmail(), retrievedUser.getEmail());

    }

}
