/**

 The UpdateItemRequest class represents a request to update an item.
 */
package no.ntnu.ecomback.model;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class UpdateItemRequest {
    /**
     * The id of the item to be updated.
     */
    @Schema(description = "The id of the item to be updated")
    private long id;

    /**
     * The updated brief description of the item.
     */
    @Schema(description = "The updated brief description of the item")
    private String briefDescription;

    /**
     * The updated full description of the item.
     */
    @Schema(description = "The updated full description of the item")
    private String fullDescription;

    /**
     * The updated category of the item.
     */
    @Schema(description = "The updated category of the item")
    private Category category;

    /**
     * The updated location of the item.
     */
    @Schema(description = "The updated location of the item")
    private Location location;

    /**
     * The updated price of the item.
     */
    @Schema(description = "The updated price of the item")
    private int price;

    /**
     * The updated images of the item.
     */
    @Schema(description = "The updated images of the item")
    private List<String> images;

    /**
     * Constructor for the UpdateItemRequest class.
     *
     * @param id               the id of the item to be updated
     * @param briefDescription the updated brief description of the item
     * @param fullDescription  the updated full description of the item
     * @param category         the updated category of the item
     * @param location         the updated location of the item
     * @param price            the updated price of the item
     * @param images           the updated images of the item
     */
    public UpdateItemRequest(long id, String briefDescription, String fullDescription, Category category,
                             Location location, int price, List<String> images) {
        this.id = id;
        this.briefDescription = briefDescription;
        this.fullDescription = fullDescription;
        this.category = category;
        this.location = location;
        this.price = price;
        this.images = images;
    }

    /**
     * Returns the id of the item to be updated.
     *
     * @return the id of the item to be updated
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the updated brief description of the item.
     *
     * @return the updated brief description of the item
     */
    public String getBriefDescription() {
        return briefDescription;
    }

    /**
     * Returns the updated full description of the item.
     *
     * @return the updated full description of the item
     */
    public String getFullDescription() {
        return fullDescription;
    }

    /**
     * Returns the updated category of the item.
     *
     * @return the updated category of the item
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Returns the updated location of the item.
     *
     * @return the updated location of the item
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the updated price of the item.
     *
     * @return the updated price of the item
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the updated images of the item.
     *
     * @return the updated images of the item
     */
    public List<String> getImages() {
        return images;
    }
}