/**

 The ItemRepository interface extends JpaRepository to provide methods for CRUD operations on items.
 */
package no.ntnu.ecomback.repository;
import no.ntnu.ecomback.model.Category;
import no.ntnu.ecomback.model.Item;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    /**
     * Saves an item entity.
     *
     * @param item the item entity to be saved
     * @param <S>  the type of the item entity
     * @return the saved item entity
     */
    @Override
    <S extends Item> S save(S item);

    /**
     * Returns a list of items containing the given keywords in their brief description, full description, or category name.
     *
     * @param briefKeyword   the keyword to search for in item brief descriptions
     * @param fullKeyword    the keyword to search for in item full descriptions
     * @param categoryKeyword the keyword to search for in category names
     * @return a list of items containing the given keywords
     */
    List<Item> findByBriefDescriptionContainingOrFullDescriptionContainingOrCategory_CategoryNameContainingOrPriceLike(String briefKeyword, String fullKeyword, String categoryKeyword, Integer priceKeyword);

    /**
     * Returns a list of items belonging to the given category.
     *
     * @param category the category whose items to retrieve
     * @return a list of items belonging to the given category
     */
    List<Item> findByCategory(Category category);

    /**
     * Returns a list of all items matching the given example.
     *
     * @param example the example item to match
     * @param <S>     the type of the example item
     * @return a list of all items matching the given example
     */
    @Override
    <S extends Item> List<S> findAll(Example<S> example);

    /**
     * Deletes all items associated with the given seller email address.
     *
     * @param seller_email the email address of the seller whose items to delete
     */
    void deleteAllBySellerEmail(String seller_email);
}