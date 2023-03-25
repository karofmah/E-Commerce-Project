package no.ntnu.ecomback.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import no.ntnu.ecomback.EcomBackApplication;
import no.ntnu.ecomback.controller.ItemController;
import no.ntnu.ecomback.repository.ItemRepository;
import no.ntnu.ecomback.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = MOCK, classes= EcomBackApplication.class)
public class BookmarkIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ItemController itemController;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;



}
