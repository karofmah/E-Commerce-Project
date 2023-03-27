/**

 The BookmarkService class provides methods for adding, retrieving, and deleting bookmarks.
 */
package no.ntnu.ecomback.service;
import no.ntnu.ecomback.model.Bookmark;
import no.ntnu.ecomback.repository.BookmarkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {
    private BookmarkRepository bookmarkRepository;

    /**
     * Sets the bookmark repository for this service.
     *
     * @param bookmarkRepository the bookmark repository to set
     */
    @Autowired
    public void setBookmarkRepository(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    private static final Logger _logger =
        LoggerFactory.getLogger(ItemService.class);

    /**
     * Adds a new bookmark to the repository.
     *
     * @param bookmark the bookmark to add
     * @return the added bookmark
     */
    public Bookmark addBookmark(Bookmark bookmark){
        try {
            _logger.info("Adding bookmark");
            return bookmarkRepository.save(bookmark);
        } catch (Exception e) {
            _logger.warn("Error occurred while adding bookmark: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a list of bookmarks for the given user email.
     *
     * @param email the email of the user whose bookmarks to retrieve
     * @return a list of bookmarks for the given user email
     */
    public List<Bookmark> getBookmarks(String email){
        try{
            _logger.info("Getting bookmarks");
            return bookmarkRepository.findByUserEmail(email);
        }catch (Exception e){
            _logger.warn("Error while getting bookmarks: " + e.getMessage());
            return null;
        }
    }

    /**
     * Deletes the bookmark with the given id from the repository.
     *
     * @param id the id of the bookmark to delete
     * @return a ResponseEntity with a HttpStatus indicating the success or failure of the deletion
     */
    public ResponseEntity<HttpStatus> deleteItem(long id) {
        try {
            bookmarkRepository.deleteById(id);
            _logger.info("Deleting item");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e){
            _logger.warn("Error deleting item: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}