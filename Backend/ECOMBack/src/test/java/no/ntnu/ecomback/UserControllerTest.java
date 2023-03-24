package no.ntnu.ecomback;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.model.Role;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.UserRepository;
import no.ntnu.ecomback.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes=EcomBackApplication.class)

public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private static UserRepository userRepository;
    List <User> mockUsers=new ArrayList<>();

    @BeforeEach
    @DisplayName("Setting up mock data for tests")
    public void setup() {


        User user1=new User("karofm@ntnu.no","Karo","Mahmoud","karofm","pw",Role.NORMAL_USER);
        User user2=new User("karofm2@ntnu.no","Karo2","Mahmoud2","karofm2","pw2",Role.NORMAL_USER);
        User user3=new User("karofm3@ntnu.no","Karo3","Mahmoud3","karofm3","pw3",Role.NORMAL_USER);

        mockUsers.add(user1);
        mockUsers.add(user2);
        mockUsers.add(user3);
        userRepository.save(user1);

        when(userRepository.findAll()).thenReturn(mockUsers);

    }
    @Test
    @WithMockUser(username = "ADMIN")
    @DisplayName("Testing the endpoint for retrieving all users")
    public void getUsers() throws Exception {


        MvcResult result = mockMvc.perform(get("/api/users/getAllUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<User> actualItems = mapper.readValue(responseString, new TypeReference<>() {
        });

        assertEquals(mockUsers.size(), actualItems.size());
        //might add assert fields
    }
    @Test
    @DisplayName("Testing the endpoint for registering a new user")
    public void registerNewUser() throws Exception {
        User newUser=new User("karofm2@ntnu.no","Karo2","Mahmoud2","karofm2","pw2",Role.NORMAL_USER);

        mockUsers.add(newUser);
        when(userService.registerUser(Mockito.any(User.class))).thenReturn(newUser);



        String newUserJson=objectMapper.writeValueAsString(newUser);

        MvcResult result= mockMvc.perform(post("/api/users/register")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUserJson)

                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        System.out.println("result: " + result);
        Assertions.assertThat(result).isNotNull();
        String userJson = result.getResponse().getContentAsString();
        Assertions.assertThat(userJson).isNotEmpty();
        Assertions.assertThat(userJson).isEqualToIgnoringCase(objectMapper.writeValueAsString(newUser));

    }

    @Test
    public void getUsersInvalid() throws Exception {


        MvcResult result = mockMvc.perform(get("/api/users/getAllUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403))
                .andReturn();
    }


}
