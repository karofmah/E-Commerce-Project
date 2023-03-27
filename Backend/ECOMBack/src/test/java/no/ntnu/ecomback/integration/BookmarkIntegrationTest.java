package no.ntnu.ecomback.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.EcomBackApplication;
import no.ntnu.ecomback.controller.BookmarkController;
import no.ntnu.ecomback.controller.ItemController;
import no.ntnu.ecomback.model.*;
import no.ntnu.ecomback.repository.BookmarkRepository;
import no.ntnu.ecomback.repository.CategoryRepository;
import no.ntnu.ecomback.repository.ItemRepository;
import no.ntnu.ecomback.repository.UserRepository;
import no.ntnu.ecomback.service.BookmarkService;
import no.ntnu.ecomback.service.ItemService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK, classes= EcomBackApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")

public class BookmarkIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    BookmarkController bookmarkController;
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Autowired
    BookmarkService bookmarkService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @BeforeEach
    public void setup(){
        User user1 = new User("karofm@ntnu.no", "Karo", "Mahmoud", "karofm", "pw", Role.NORMAL_USER);
        userRepository.save(user1);
        Location location = new Location();
        Category category=new Category("Category");
        categoryRepository.save(category);
        location.setLatitude(5);
        location.setLongitude(10);

        Item item1 = new Item(user1, "Description 1", "Full description 1",category, location, 150);
        itemRepository.save(item1);

        Bookmark bookmark1=new Bookmark();
        bookmark1.setUser(user1);
        bookmark1.setItem(item1);
        bookmarkRepository.save(bookmark1);
    }

    @AfterEach
    public void teardown(){
        bookmarkRepository.deleteAll();
        itemRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
    }
    @Test
    @WithMockUser(username = "USER")
    @DisplayName("Testing the endpoint for retrieving all bookmarks as a valid user")
    public void getBookmarksByEmail() throws Exception {


        MvcResult result = mockMvc.perform(get("/api/bookmark/get?email=karofm@ntnu.no")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<Bookmark> actualBookmarks = mapper.readValue(responseString, new TypeReference<>() {
        });
        System.out.println(bookmarkRepository.findAll().size());
        Assertions.assertEquals(bookmarkRepository.findAll().size(), actualBookmarks.size());
    }

    @Test
    @WithMockUser(username = "USER")
    @DisplayName("Testing the endpoint for adding a valid bookmark")
    public void addValidBookmark() throws Exception {
        User newUser = new User("karofm2@ntnu.no", "Karo2", "Mahmoud2", "karofm2", "pw2", Role.NORMAL_USER);
        userRepository.save(newUser);
        Location location = new Location();
        Category category=new Category("Category");
        categoryRepository.save(category);
        location.setLatitude(5);
        location.setLongitude(10);

        Item newItem = new Item(newUser, "Description 1", "Full description 1",category, location, 150);
        itemRepository.save(newItem);

        Bookmark newBookmark=new Bookmark();
        newBookmark.setUser(newUser);
        newBookmark.setItem(newItem);

        String newBookmarkJson = objectMapper.writeValueAsString(newBookmark);

        mockMvc.perform(post("/api/bookmark/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBookmarkJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());


        System.out.println(bookmarkRepository.findAll());
        Bookmark retrievedMessage = bookmarkRepository.findAll()
                .get(bookmarkRepository.findAll().size() - 1);

        Assertions.assertNotEquals(0, retrievedMessage.getId());
        Assertions.assertEquals(newBookmark.getItem().getFullDescription(), retrievedMessage.getItem().getFullDescription());
        Assertions.assertEquals(newBookmark.getUser().getEmail(),retrievedMessage.getUser().getEmail());

    }

}
