package no.ntnu.ecomback.service;

import no.ntnu.ecomback.model.Bookmark;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.repository.BookmarkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkService {

    private BookmarkRepository bookmarkRepository;

    @Autowired
    public void setBookmarkRepository(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    private static final Logger _logger =
            LoggerFactory.getLogger(ItemService.class);
    public Bookmark addBookmark(Bookmark bookmark){
        try {
            _logger.info("Adding bookmark");
            return bookmarkRepository.save(bookmark);
        } catch (Exception e) {
            _logger.warn("Error occurred while adding bookmark: " + e.getMessage());
            return null;
        }
    }
    public List<Bookmark> getBookmarks(User user){
        try{
            _logger.info("Getting bookmarks");
            return bookmarkRepository.findByUser(user);
        }catch (Exception e){
            _logger.warn("Error while getting bookmarks: " + e.getMessage());
            return null;
        }
    }
}
