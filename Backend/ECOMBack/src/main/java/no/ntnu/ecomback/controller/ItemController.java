package no.ntnu.ecomback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.model.Item;
import no.ntnu.ecomback.model.UpdateItemRequest;
import no.ntnu.ecomback.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/items")
@CrossOrigin("http://localhost:5173/")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "Add a new item")
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Item> addItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Item object to be created")
            @RequestBody Item item){
        try{
            Item _item=itemService.addItem(item);
            return new ResponseEntity<>(_item,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update an item")
    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public Item updateItem(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The updated item object")
            @RequestBody UpdateItemRequest updateItemRequest){
        return itemService.updateItem(updateItemRequest);
    }

    @Operation(summary = "Delete an item")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteItem(
            @Parameter(description = "The id of the item")
            @PathVariable long id){
        return itemService.deleteItem(id);
    }

    @Operation(summary = "Get all items")
    @GetMapping("/getItems")
    public ResponseEntity<List<Item>> getItems() {
        try {
            List<Item> items = itemService.getItems();
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get items by user")
    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public List<Item> getItemsByUserEmail(
            @Parameter(description = "The user email of the seller of the item")
            @RequestParam String email){
        return itemService.getItemsByUserEmail(email);
    }

    @Operation(summary = "Get a specific item")
    @GetMapping("{id}")
    public Optional<Item> getItemById(
            @Parameter(description = "The id of the item")
            @PathVariable long id){
        return itemService.getItemById(id);
    }

    @Operation(summary = "Get items by keyword")
    @GetMapping("/get/keyword")
    public List<Item> getItemsByKeyword(
            @Parameter(description = "The keyword to be searched for")
            @RequestParam String keyword){
        return itemService.getItemsByKeyword(keyword);
    }

    @Operation(summary = "Get items by category")
    @GetMapping("/get/category")
    public List<Item> getItemsByCategory(
            @Parameter(description = "The item category to be searched for")
            @RequestParam Category category){
        return itemService.getItemsByCategory(category);
    }
}
