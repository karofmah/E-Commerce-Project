package no.ntnu.ecomback.service;

import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.model.Item;
import no.ntnu.ecomback.model.UpdateItemRequest;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.ItemRepository;
import no.ntnu.ecomback.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger _logger =
            LoggerFactory.getLogger(ItemService.class);
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
                if(u.getEmail().equals(item.getSeller().getEmail())){
                    _logger.info("Item added");
                    return itemRepository.save(item);
                }
            }
            _logger.info("Seller does not exist ");
            return null;
        } catch (Exception e) {
            _logger.warn("Error occurred while adding item: " + e.getMessage());
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
                _logger.info("Item is updating");
                return itemRepository.save(_item);
            }
        }catch (Exception e){
            _logger.warn("Error updating item: " + e.getMessage());
            return null;
        }
        _logger.info("Item does not exist ");
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
            _logger.info("Getting items");
            return new ArrayList<>(itemRepository.findAll());
        } catch (Exception e) {
            _logger.warn("Error getting all items: " + e.getMessage());
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
                _logger.info("Getting items");
            } catch (Exception e) {
                _logger.warn("Error getting items: " + e.getMessage());
                myItems = null;
            }
            return myItems;
    }
    public Optional<Item> getItemById(long id){
        try{
            _logger.info("Getting item");
            return itemRepository.findById(id);
        }catch (Exception e){
            _logger.warn("Error while getting item: " + e.getMessage());
            return Optional.empty();
        }
    }
    public List<Item> getItemsByKeyword(String keyword){
        try{
            _logger.info("Getting items");
            return itemRepository.
                    findByBriefDescriptionContainingOrFullDescriptionContainingOrCategoryContaining(keyword,keyword,keyword);
        }catch (Exception e){
            _logger.warn("Error while getting items by description: " + e.getMessage());
            return null;
        }
    }
    public List<Item> getItemsByCategory(Category category){
        try{
            _logger.info("Getting items");
            return itemRepository.findByCategory(category);
        }catch (Exception e){
            System.out.println("Error while getting items by category: "+ e.getMessage());
            return null;
        }
    }
}
