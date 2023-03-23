/**

 The Bookmark class represents a bookmark in the application.
 It is an entity that is mapped to a database table using the Jakarta Persistence API.
 */
package no.ntnu.ecomback.model;
import jakarta.persistence.*;

@Entity
@Table
public class Bookmark {
    /**
     * The unique identifier of the bookmark.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * The user who created the bookmark.
     */
    @ManyToOne
    @JoinColumn(name = "user_email")
    private User user;

    /**
     * The item that is bookmarked.
     */
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    /**
     * Constructs an empty Bookmark object.
     */
    public Bookmark() {
    }

    /**
     * Constructs a Bookmark object with the specified id, user, and item.
     *
     * @param id   the unique identifier of the bookmark
     * @param user the user who created the bookmark
     * @param item the item that is bookmarked
     */
    public Bookmark(long id, User user, Item item) {
        this.id = id;
        this.user = user;
        this.item = item;
    }

    /**
     * Returns the unique identifier of the bookmark.
     *
     * @return the unique identifier of the bookmark
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the user who created the bookmark.
     *
     * @return the user who created the bookmark
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user who created the bookmark.
     *
     * @param user the user who created the bookmark
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the item that is bookmarked.
     *
     * @return the item that is bookmarked
     */
    public Item getItem() {
        return item;
    }

    /**
     * Sets the item that is bookmarked.
     *
     * @param item the item that is bookmarked
     */
    public void setItem(Item item) {
        this.item = item;
    }

}