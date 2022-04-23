package com.example.uimastyleit;

public class Item {
    private User user;
    private String title;
    private String condition;
    private String description;
    private String imageUrl;
    private String size;
    private int price;

    public Item(User user, String title, String condition, String description, String size, int price, String imageUrl) {
        this.user = user;
        this.condition = condition;
        this.description = description;
        this.title = title;
        this.size = size;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getTitle() { return this.title; }
    public int getPrice() { return this.price; }
    public String getSize() { return this.size; }
    public String getCondition() { return this.condition; }
    public String getDescription() { return this.description; }
    public String getImageUrl() { return this.imageUrl; }
    public User getUser() { return this.user; }

}
