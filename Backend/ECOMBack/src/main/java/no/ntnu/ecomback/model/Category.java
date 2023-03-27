/**

 The Category class represents the different categories that a user of the e-commerce application can have.
 */
package no.ntnu.ecomback.model;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Category {
    /**
     * The name of the category
     */
    @Id
    @Schema(description = "The name of the category")
    private String categoryName;

    /**
     * Default constructor for the Category class.
     */
    public Category() {
    }

    /**
     * Constructor for the Category class that takes in the name of the category.
     *
     * @param categoryName the name of the category
     */
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Returns the name of the category.
     *
     * @return the name of the category
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the name of the category.
     *
     * @param categoryName the name of the category
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Returns a string representation of the category.
     *
     * @return the name of the category
     */
    @Override
    public String toString() {
        return categoryName;
    }
}