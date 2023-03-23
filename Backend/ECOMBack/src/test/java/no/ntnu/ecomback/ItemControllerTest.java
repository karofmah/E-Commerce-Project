package no.ntnu.ecomback;

import no.ntnu.ecomback.model.*;
import no.ntnu.ecomback.repository.ItemRepository;
import no.ntnu.ecomback.service.ItemService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK, classes=EcomBackApplication.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private static ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

     List<Item> mockItems=new ArrayList<>();

    @BeforeEach
    @DisplayName("Setting up mock data for tests")
    public void setup() {
        User user1=new User("karofm@ntnu.no","Karo","Mahmoud","karofm","pw",Role.NORMAL_USER);
        Location location=new Location();
        location.setLatitude(5);
        location.setLongitude(10);
        List<String> images=new ArrayList<>();
        images.add("image1");
        mockItems.add(new Item(user1, "Description 1", "Full description 1",new Category("Category 1"),location ,100));
        mockItems.add(new Item(user1, "Description 2", "Full description 2",new Category("Category 2"),location ,150));
        mockItems.add(new Item(user1, "Description 3", "Full description 3",new Category("Category 3"),location ,200));

        // Set up mock behavior for ItemRepository
        when(itemRepository.findAll()).thenReturn(mockItems);
    }

    @Test
    @DisplayName("Testing the endpoint for retrieving all items")
    public void testGetItems() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/items/getItems")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<Item> actualItems = mapper.readValue(responseString, new TypeReference<>() {
        });

        assertEquals(mockItems.size(), actualItems.size());
    }
    
}
