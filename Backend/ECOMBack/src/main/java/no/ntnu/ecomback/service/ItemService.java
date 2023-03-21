package no.ntnu.ecomback.service;

import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.model.Item;
import no.ntnu.ecomback.model.UpdateItemRequest;
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
import java.util.stream.Collectors;

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
            System.out.println("Error occurred while adding item: " + e.getMessage());
            return null;
        }
    }

    public Item updateItem(UpdateItemRequest updateItemRequest){
        try{
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
            }
        }catch (Exception e){
            System.out.println("Error updating item: " + e.getMessage());
            return null;
        }
        return null;
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
            return new ArrayList<>(itemRepository.findAll());
        } catch (Exception e) {
            System.out.println("Error getting all items: " + e.getMessage());
            return null;
        }

    }
    public List<Item> getItemsByUserEmail(String email){

            List<Item> myItems;
            try {
                myItems = itemRepository.findAll()
                        .stream()
                        .filter(i -> i.getSeller().getEmail().equals(email))
                        .collect(Collectors.toList());
            } catch (Exception e) {
                System.out.println("Error getting items: " + e.getMessage());
                myItems = null;
            }
            return myItems;
    }
    public Optional<Item> getItemById(long id){
        try{
            return itemRepository.findById(id);
        }catch (Exception e){
            System.out.println("Error while getting item: " + e.getMessage());
            return Optional.empty();
        }
    }
    public List<Item> getItemsByKeyword(String keyword){
        try{
            return itemRepository.
                    findByBriefDescriptionContainingOrFullDescriptionContainingOrCategoryContaining(keyword,keyword,keyword);
        }catch (Exception e){
            System.out.println("Error while finding items by description: " + e.getMessage());
            return null;
        }
    }
    public List<Item> getItemsByCategory(Category category){
        try{

            return itemRepository.findByCategory(category);
        }catch (Exception e){
            System.out.println("Error while finding items by category: "+ e.getMessage());
            return null;
        }
    }
}
