package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.model.Item;
import no.ntnu.ecomback.model.UpdateItemRequest;
import no.ntnu.ecomback.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        System.out.println(item);
        return itemService.addItem(item);
    }

    @PutMapping("/update")
    public Item updateItem(@RequestBody UpdateItemRequest updateItemRequest){
        return itemService.updateItem(updateItemRequest);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable long id){
        return itemService.deleteItem(id);
    }
    @GetMapping("/getItems")
    public ResponseEntity<List<Item>> getItems() {
        try {
            List<Item> items = itemService.getItems();

            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("")
    public List<Item> getItemsByUserEmail(@RequestParam String email){
        return itemService.getItemsByUserEmail(email);
    }
    @GetMapping("{id}")
    public Optional<Item> getItemById(@PathVariable long id){
        return itemService.getItemById(id);
    }
    @GetMapping("/get/keyword")
    public List<Item> getItemsByKeyword(@RequestParam String keyword){
        return itemService.getItemsByKeyword(keyword);
    }
    @GetMapping("/get/category")
    public List<Item> getItemsByCategory(@RequestParam Category category){
        return itemService.getItemsByCategory(category);
    }
}
