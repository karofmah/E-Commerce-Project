/**

 The CategoryRepository interface extends JpaRepository to provide methods for CRUD operations on categories.
 */
package no.ntnu.ecomback.repository;
import no.ntnu.ecomback.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    /**
     * Saves a category entity.
     *
     * @param entity the category entity to be saved
     * @param <S>    the type of the category entity
     * @return the saved category entity
     */
    @Override
    <S extends Category> S save(S entity);
}