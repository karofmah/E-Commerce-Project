package no.ntnu.ecomback.service;

import no.ntnu.ecomback.model.Item;
import no.ntnu.ecomback.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item addItem(Item item){
        try {
            for (Item i:itemRepository.findAll()) {
                if(i.getSeller().getEmail().equals(item.getSeller().getEmail())){
                    return itemRepository.save(item);
                }
            }
            System.out.println("rip");
           return null;
        } catch (Exception e) {
            System.out.println("Error occurred while registering user: " + e.getMessage());
            return null;
        }
    }
}
