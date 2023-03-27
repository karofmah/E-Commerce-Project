/**

 Service class for managing {@link Category} objects in the system.
 */
package no.ntnu.ecomback.service;
import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    private static final Logger _logger = LoggerFactory.getLogger(ItemService.class);

    /**
     * Sets the {@link CategoryRepository} to use for interacting with the database.
     *
     * @param categoryRepository the category repository to set.
     */
    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Gets all categories from the database.
     *
     * @return a list of all categories.
     */
    public List<Category> getAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            _logger.warn("Error getting all categories: " + e.getMessage());
            return null;
        }
    }

    /**
     * Adds a new category to the database.
     *
     * @param category the category to add.
     * @return the added category.
     */
    public Category addCategory(Category category) {
        try {
            return categoryRepository.save(new Category(category.getCategoryName()));
        } catch (Exception e) {
            _logger.warn("Error getting category: " + e.getMessage());
            return null;
        }
    }

    /**
     * Deletes a category from the database.
     *
     * @param categoryName the name of the category to delete.
     * @return true if the category was successfully deleted, false otherwise.
     */
    public boolean deleteCategory(String categoryName) {
        try {
            categoryRepository.deleteById(categoryName);
            return true;
        } catch (Exception e) {
            _logger.warn("Error deleting category: " + e.getMessage());
            return false;
        }
    }
}