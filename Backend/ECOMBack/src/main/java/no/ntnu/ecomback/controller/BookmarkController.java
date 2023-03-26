package no.ntnu.ecomback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import no.ntnu.ecomback.model.Bookmark;
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
    public Bookmark addBookmark(@RequestBody(description = "The bookmark to be added") Bookmark bookmark){
        return bookmarkService.addBookmark(bookmark);
    }

    @Operation(summary = "Get bookmarks by user")
    @GetMapping("/get")
    @PreAuthorize("hasRole('USER')")
    public List<Bookmark> getBookmarks(
            @Parameter(description = "The email of the user") @RequestParam String email){
        return bookmarkService.getBookmarks(email);
    }

    @Operation(summary = "Delete a bookmark")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<HttpStatus> deleteBookmark(
            @Parameter(description = "The id of the bookmark to be deleted") @PathVariable long id){
        return bookmarkService.deleteItem(id);
    }
}
