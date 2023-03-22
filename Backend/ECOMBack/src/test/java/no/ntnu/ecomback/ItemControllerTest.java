package no.ntnu.ecomback;

import no.ntnu.ecomback.model.*;
import no.ntnu.ecomback.service.ItemService;
import org.junit.Test;
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
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetItems() throws Exception {
/*
        User seller1 = new User("karofm3@ntnu.no","Karo", "Mahmoud", "karofm", "pw", Role.NORMAL_USER);
        Location location1 = new Location();
        location1.setLatitude(45.5);
        location1.setLatitude(50.0);
        List<String> images1 = new ArrayList<>();
        images1.add("image1");
        images1.add("image2");
        Item item1 = new Item(seller1,"item","about item", Category.REAL_ESTATE,location1,100);
        List<Item> expectedItemList = Arrays.asList(item1);

        MvcResult result = mockMvc.perform(get("/api/items/getItems")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TypeReference<List<Item>> typeReference = new TypeReference<>() {};
        List<Item> actualItemList = objectMapper.readValue(result.getResponse().getContentAsString(), typeReference);


        assertEquals(expectedItemList, actualItemList);

*/
    }
}
