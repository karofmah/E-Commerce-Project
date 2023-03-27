package no.ntnu.ecomback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import no.ntnu.ecomback.model.Bookmark;
import no.ntnu.ecomback.model.Item;
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


    @Operation(summary = "Add a new bookmark")
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<Bookmark> addBookmark(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The bookmark to be added")
            @RequestBody Bookmark bookmark){
        try{
            Bookmark _bookmark=bookmarkService.addBookmark(bookmark);
            return new ResponseEntity<>(_bookmark,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get bookmarks by user")
    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Bookmark>> getBookmarks(
            @Parameter(description = "The email of the user") @RequestParam String email){
        try {
            List<Bookmark> bookmarks = bookmarkService.getBookmarks(email);
            return new ResponseEntity<>(bookmarks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a bookmark")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteBookmark(
            @Parameter(description = "The id of the bookmark to be deleted") @PathVariable long id){
        return bookmarkService.deleteItem(id);
    }
}
