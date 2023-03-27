package no.ntnu.ecomback.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class UpdateItemRequest {
    @Schema(description = "The id of the item to be updated")
    private long id;
    @Schema(description = "The updated brief description of the item")
    private String briefDescription;
    @Schema(description = "The updated full description of the item")
    private String fullDescription;
    @Schema(description = "The updated category of the item")
    private Category category;
    @Schema(description = "The updated location of the item")
    private Location location;
    @Schema(description = "The updated price of the item")
    private int price;
    @Schema(description = "The updated images of the item")
    private List<String> images;
    public UpdateItemRequest(long id, String briefDescription
            , String fullDescription, Category category,
                             Location location, int price,List<String> images) {
        this.id = id;
        this.briefDescription = briefDescription;
        this.fullDescription = fullDescription;
        this.category = category;
        this.location = location;
        this.price = price;
        this.images=images;
    }

    public long getId() {
        return id;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public Category getCategory() {
        return category;
    }

    public Location getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public List<String> getImages() {
        return images;
    }
}
