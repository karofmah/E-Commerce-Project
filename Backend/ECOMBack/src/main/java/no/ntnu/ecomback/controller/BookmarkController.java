package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.Bookmark;
import no.ntnu.ecomback.model.User;
import no.ntnu.ecomback.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173/")

@RestController
@RequestMapping("/api/bookmark")
public class BookmarkController {



    private BookmarkService bookmarkService;

    @Autowired
    public void setBookmarkService(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public Bookmark addBookmark(@RequestBody Bookmark bookmark){
        return bookmarkService.addBookmark(bookmark);
    }
    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public List<Bookmark> getBookmarks(@RequestBody User user){
        return bookmarkService.getBookmarks(user);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteBookmark(@PathVariable long id){
        return bookmarkService.deleteItem(id);
    }
}
