package no.ntnu.ecomback.service;

import no.ntnu.ecomback.model.Item;
import no.ntnu.ecomback.model.UpdateItemRequest;
import no.ntnu.ecomback.model.UpdateUserRequest;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.ItemRepository;
import no.ntnu.ecomback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private UserRepository userRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public Item addItem(Item item){
        try {
            for (User u:userRepository.findAll()) {
                System.out.println(u.getEmail());
                if(u.getEmail().equals(item.getSeller().getEmail())){
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

    public Item updateItem(UpdateItemRequest updateItemRequest){

        Optional<Item> itemById = itemRepository.findById(updateItemRequest.getId());

        if (itemById.isPresent()) {
            Item _item = itemById.get();
            _item.setBriefDescription(updateItemRequest.getBriefDescription());
            _item.setFullDescription(updateItemRequest.getFullDescription());
            _item.setCategory(updateItemRequest.getCategory());
            _item.setLocation(updateItemRequest.getLocation());
            _item.setPrice(updateItemRequest.getPrice());
            _item.setImages(updateItemRequest.getImages());

            return itemRepository.save(_item);
        } else {
            return null;
        }
    }
    public ResponseEntity <HttpStatus> deleteItem(long id) {
        try {
            itemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    public List<Item> getItems(){
        try {

            List<Item> items = new ArrayList<>(itemRepository.findAll());

            System.out.println("rip");
            return items;
        } catch (Exception e) {
            System.out.println("Error occurred while registering user: " + e.getMessage());
            return null;
        }
    }
}
