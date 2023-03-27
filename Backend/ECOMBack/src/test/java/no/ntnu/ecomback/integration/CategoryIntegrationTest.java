package no.ntnu.ecomback.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.EcomBackApplication;
import no.ntnu.ecomback.controller.CategoryController;
import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.repository.CategoryRepository;
import no.ntnu.ecomback.service.CategoryService;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK, classes= EcomBackApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CategoryIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    CategoryController categoryController;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;

    @DisplayName("Setup")
    @BeforeEach
    public void setup(){
        Category category=new Category("Category");
        categoryRepository.save(category);
    }

    @DisplayName("Delete categoriesRepository table")
    @AfterEach
    public void teardown(){
        categoryRepository.deleteAll();
    }
    @Test
    @WithMockUser(username = "USER")
    @DisplayName("Testing the endpoint for retrieving all categories")
    public void getCategories() throws Exception {

        MvcResult result = mockMvc.perform(get("/api/categories/getCategories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<Category> actualCategories= mapper.readValue(responseString, new TypeReference<>() {
        });

        Assertions.assertEquals(categoryRepository.findAll().size(),actualCategories.size());
    }

    @Test
    @WithMockUser(username = "USER")
    @DisplayName("Testing the endpoint for adding a new category")
    public void addNewCategory() throws Exception {
        Category newCategory=new Category("New Category");

        String newCategoryJson=objectMapper.writeValueAsString(newCategory);

        mockMvc.perform(post("/api/categories/addCategory")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCategoryJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Optional<Category> categoryOptional = categoryRepository.findById(newCategory.getCategoryName());
        Assertions.assertTrue(categoryOptional.isPresent());
        Category retrievedCategory= categoryOptional.get();
        Assertions.assertEquals(newCategory.getCategoryName(), retrievedCategory.getCategoryName());

    }
}
