package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.Item;
import no.ntnu.ecomback.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/items")
@CrossOrigin("http://localhost:5173/")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/add")
    public Item addItem(@RequestBody Item item){
        return itemService.addItem(item);
    }

    @GetMapping("/getItems")
    public ResponseEntity<List<Item>> getItems() {
        try {
            List<Item> items = new ArrayList<>();

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
