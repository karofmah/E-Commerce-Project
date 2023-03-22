package no.ntnu.ecomback.service;

import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.repository.CategoryRepository;
import no.ntnu.ecomback.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    private static final Logger _logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Category addCategory(Category category) {
        try {
            return categoryRepository.save(new Category(category.getCategoryName()));
        } catch (Exception e) {
            return null;
        }
    }

    public boolean deleteCategory(String categoryName) {
        try {
            categoryRepository.deleteById(categoryName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
