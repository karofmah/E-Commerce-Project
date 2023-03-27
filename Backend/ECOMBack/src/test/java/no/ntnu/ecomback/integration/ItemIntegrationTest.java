
package no.ntnu.ecomback.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.EcomBackApplication;
import no.ntnu.ecomback.controller.ItemController;
import no.ntnu.ecomback.model.*;
import no.ntnu.ecomback.repository.CategoryRepository;
import no.ntnu.ecomback.repository.ItemRepository;
import no.ntnu.ecomback.repository.UserRepository;
import no.ntnu.ecomback.service.ItemService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK, classes= EcomBackApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItemIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ItemController itemController;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemService itemService;


    @BeforeEach
    @DisplayName("Setting up mock data for tests")
    public void setup() {

        User user1 = new User("karofm@ntnu.no", "Karo", "Mahmoud", "karofm", "pw", Role.NORMAL_USER);
        userRepository.save(user1);
        Location location = new Location();


        Category category=new Category("Category");
        categoryRepository.save(category);
        location.setLatitude(5);
        location.setLongitude(10);

        Item item1 = new Item(user1, "Description 1", "Full description 1",category, location, 150);
        System.out.println(item1.getId());

        itemRepository.save(item1);
        System.out.println(item1.getId());

    }
    @AfterEach
    public void teardown(){
        itemRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();

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

        System.out.println(actualItems);
    }

    @Nested
    class TestAddItem {


        @Test
        @WithMockUser(username = "USER")
        @DisplayName("Testing the endpoint for adding an item as a valid user")
        public void addItemValid() throws Exception {

            User newUser = new User("karofm2@ntnu.no", "Karo2", "Mahmoud2", "karofm2", "pw2", Role.NORMAL_USER);
            userRepository.save(newUser);
            Location newLocation = new Location();
            newLocation.setLatitude(25);
            newLocation.setLongitude(50);
            Category newCategory=new Category("Category 2");
            categoryRepository.save(newCategory);
            Item newItem = new Item(newUser, "Description 2", "Full description 2", newCategory, newLocation , 200);
            List <String> imageList=new ArrayList<>();
            imageList.add("image");
            newItem.setImages(imageList);
            String newItemJson = objectMapper.writeValueAsString(newItem);

             mockMvc.perform(post("/api/items/add")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(newItemJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated());


            Item retrievedItem = itemRepository.findAll()
                    .get(itemRepository.findAll().size() - 1);

            Assertions.assertNotEquals(0, retrievedItem.getId());
            Assertions.assertEquals(newItem.getFullDescription(), retrievedItem.getFullDescription());
            Assertions.assertEquals(newItem.getBriefDescription(), retrievedItem.getBriefDescription());

        }

    }
}


