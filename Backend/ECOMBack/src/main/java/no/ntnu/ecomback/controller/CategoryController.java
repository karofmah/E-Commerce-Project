package no.ntnu.ecomback.controller;

import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/getCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addCategory")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        try {
            Category _category = categoryService.addCategory(category);
            return  new ResponseEntity<>(_category, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteCategory/{categoryName}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("categoryName") String categoryName) {
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
