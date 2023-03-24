package no.ntnu.ecomback.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.EcomBackApplication;
import no.ntnu.ecomback.model.*;
import no.ntnu.ecomback.repository.ItemRepository;
import no.ntnu.ecomback.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK, classes= EcomBackApplication.class)
public class ItemIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ItemController itemController;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    private ItemService itemService;

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
        mockItems.get(0).setImages(images);
        mockItems.add(new Item(user1, "Description 1", "Full description 1",new Category("Category 1"),location ,100));
        mockItems.add(new Item(user1, "Description 2", "Full description 2",new Category("Category 2"),location ,150));
        mockItems.add(new Item(user1, "Description 3", "Full description 3",new Category("Category 3"),location ,200));


        when(itemRepository.findAll()).thenReturn(mockItems);
    }

    @Test
    @DisplayName("Testing the endpoint for retrieving all items")
    public void getItems() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/items/getItems")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<Item> actualItems = mapper.readValue(responseString, new TypeReference<>() {
        });

        assertEquals(mockItems.size(), actualItems.size());
        //might add assert fields
    }
    @Test
    @DisplayName("Testing the endpoint for adding an item as an invalid user")
    public void addItemInvalid() throws Exception {
        User newUser=new User("karofm2@ntnu.no","Karo2","Mahmoud2","karofm2","pw2",Role.NORMAL_USER);
        Location location2=new Location();
        location2.setLatitude(25);
        location2.setLongitude(50);
        Item newItem=new Item(newUser, "Description 4", "Full description 4",new Category("Category 4"),location2 ,100);

        when(itemService.addItem(Mockito.any(Item.class))).thenReturn(newItem);

        String newItemJson=objectMapper.writeValueAsString(newItem);

        MvcResult result= mockMvc.perform(post("/api/items/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newItemJson)

                )
                .andExpect(MockMvcResultMatchers.status().is(403))
                .andReturn();


    }
    @Test
    @WithMockUser(username = "USER")
    @DisplayName("Testing the endpoint for adding an item as a valid user")
    public void addItemValid() throws Exception {
        User newUser=new User("karofm2@ntnu.no","Karo2","Mahmoud2","karofm2","pw2",Role.NORMAL_USER);
        Location location2=new Location();
        location2.setLatitude(25);
        location2.setLongitude(50);
        Item newItem=new Item(newUser, "Description 4", "Full description 4",new Category("Category 4"),location2 ,100);

        when(itemService.addItem(Mockito.any(Item.class))).thenReturn(newItem);

        String newItemJson=objectMapper.writeValueAsString(newItem);

        MvcResult result= mockMvc.perform(post("/api/items/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newItemJson)

                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String itemJson = result.getResponse().getContentAsString();
        Assertions.assertThat(itemJson).isNotEmpty();
        Assertions.assertThat(itemJson).isEqualToIgnoringCase(objectMapper.writeValueAsString(newItem));


    }


}
