/**

 The BookmarkRepository interface extends JpaRepository to provide methods for CRUD operations on bookmarks.
 */
package no.ntnu.ecomback.repository;
import no.ntnu.ecomback.model.Bookmark;
import no.ntnu.ecomback.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    /**
     * Saves a bookmark entity.
     *
     * @param entity the bookmark entity to be saved
     * @param <S>    the type of the bookmark entity
     * @return the saved bookmark entity
     */
    @Override
    <S extends Bookmark> S save(S entity);

    /**
     * Returns a list of bookmarks belonging to a user with the given email address.
     *
     * @param email the email address of the user whose bookmarks to retrieve
     * @return a list of bookmarks belonging to the user with the given email address
     */
    List<Bookmark> findByUserEmail(String email);

    /**
     * Deletes all bookmarks associated with the given item.
     *
     * @param item the item whose bookmarks to delete
     */
    void deleteBookmarksByItem(Item item);

    /**
     * Deletes a bookmark associated with a user with the given email address.
     *
     * @param user_email the email address of the user whose bookmark to delete
     */
    void deleteBookmarkByUserEmail(String user_email);
}