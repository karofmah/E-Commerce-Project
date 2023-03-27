package no.ntnu.ecomback.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:5173/")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories")
    @GetMapping("/getCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Add a new category")
    @PostMapping("/addCategory")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Category> addCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The category to be added")
            @RequestBody Category category) {
        try {
            Category _category = categoryService.addCategory(category);
            return  new ResponseEntity<>(_category, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a category")
    @DeleteMapping("/deleteCategory/{categoryName}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Category> deleteCategory(
            @Parameter(description = "The name of the category to be deleted")
            @PathVariable("categoryName") String categoryName) {
        try {
            if (categoryService.deleteCategory(categoryName)) {
                return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
