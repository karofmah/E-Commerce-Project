package no.ntnu.ecomback.model;

import java.util.List;

public class UpdateItemRequest {
    private long id;
    private String briefDescription;
    private String fullDescription;
    private Category category;
    private Location location;
    private int price;

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
