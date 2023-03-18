package no.ntnu.ecomback.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "briefDescription")
    private String briefDescription;

    @Column(name = "fullDescription")
    private String fullDescription;

    @Column(name = "category")
    private Category category;

    //create location class?
    @Column(name = "location")
    private String location;

    @Column(name = "price")
    private int price;

    public Item() {

    }

    public Item(String briefDescription, String fullDescription, Category category, String location, int price) {
        this.briefDescription = briefDescription;
        this.fullDescription = fullDescription;
        this.category = category;
        this.location = location;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}