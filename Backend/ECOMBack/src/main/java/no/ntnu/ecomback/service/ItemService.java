/**

 This package contains classes related to the service layer of the application.
 The service layer contains the business logic of the application and communicates with
 the repository layer to interact with the database.
 It also receives requests from the controller layer, processes them and returns the appropriate responses.
 */
package no.ntnu.ecomback.service;
import jakarta.transaction.Transactional;
import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.model.Item;
import no.ntnu.ecomback.model.UpdateItemRequest;
import no.ntnu.ecomback.repository.BookmarkRepository;
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
    private BookmarkRepository bookmarkRepository;

    /**
     * Logger to log events and debug messages in ItemService class.
     */
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

    @Autowired
    public void setBookmarkRepository(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    /**
     * Adds an item to the database.
     * @param item the item to be added.
     * @return the added item or null if adding the item fails.
     */
    public Item addItem(Item item){
        try {
            _logger.info("Adding item");
            item=itemRepository.save(item);
            return item;
        } catch (Exception e) {
            _logger.warn("Error occurred while adding item: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates an existing item in the database.
     * @param updateItemRequest a request object containing the updated values of the item.
     * @return the updated item or null if updating the item fails.
     */
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

    /**
     * Deletes an item from the database.
     * @param id the ID of the item to be deleted.
     * @return an HTTP status indicating the success or failure of the deletion operation.
     */
    @Transactional
    public ResponseEntity <HttpStatus> deleteItem(long id) {
        try {
            bookmarkRepository.deleteBookmarksByItem(itemRepository.findById(id).get());
            itemRepository.deleteById(id);
            _logger.info("Deleting item");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e){
            _logger.warn("Error deleting item: " + String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**

     Retrieves all items from the item repository.
     @return a list of all items in the item repository, or null if an error occurs.
     */
    public List<Item> getItems(){
        try {
            _logger.info("Getting items");
            return new ArrayList<>(itemRepository.findAll());
        } catch (Exception e) {
            _logger.warn("Error getting all items: " + e.getMessage());
            return null;
        }
    }
    /**

     Retrieves items from the item repository that belong to the specified user.
     @param email the email address of the user whose items should be retrieved.
     @return a list of items that belong to the specified user, or null if an error occurs.
     */
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
    /**

     Retrieves an item from the item repository by its ID.
     @param id the ID of the item to retrieve.
     @return an Optional containing the retrieved item, or an empty Optional if no item with the specified ID was found or an error occurs.
     */
    public Optional<Item> getItemById(long id){
        try{
            _logger.info("Getting item");
            return itemRepository.findById(id);
        }catch (Exception e){
            _logger.warn("Error while getting item: " + e.getMessage());
            return Optional.empty();
        }
    }
    /**

     Retrieves items from the item repository that contain the specified keyword in their brief description, full description, or category name.
     @param keyword the keyword to search for.
     @return a list of items that match the search keyword, or null if an error occurs.
     */
    public List<Item> getItemsByKeyword(String keyword) {
        try {
            _logger.info("Getting items");
            Integer priceKeyword = null;
            try {
                priceKeyword = Integer.parseInt(keyword);
            } catch (NumberFormatException e) {
                // keyword is not a valid integer, ignore
            }
            return itemRepository.findByBriefDescriptionContainingOrFullDescriptionContainingOrCategory_CategoryNameContainingOrPriceLike(keyword, keyword, keyword, priceKeyword);
        } catch (Exception e) {
            _logger.warn("Error while getting items by description: " + e.getMessage());
            return null;
        }
    }
    /**

     Retrieves items from the item repository that belong to the specified category.
     @param category the category to retrieve items for.
     @return a list of items that belong to the specified category, or null if an error occurs.
     */
    public List<Item> getItemsByCategory(Category category){
        try{
            _logger.info("Getting items");
            return itemRepository.findByCategory(category);
        }catch (Exception e){
            _logger.warn("Error while getting items by category: "+ e.getMessage());
            return null;
        }
    }
}
