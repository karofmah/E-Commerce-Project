package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.Item;
import no.ntnu.ecomback.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/items")
@CrossOrigin("http://localhost:5174/")
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
}
