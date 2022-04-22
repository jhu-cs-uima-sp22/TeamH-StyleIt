package com.example.uimastyleit;

public class Item {
    private User user;
    private String title;
    private String description;
    private String imageUrl;
    private String size;
    private int price;

    public Item(User user, String title, String description, String size, int price) {
        this.user = user;
        this.description = description;
        this.title = title;
        this.size = size;
        this.price = price;
    }

    public String getTitle() { return this.title; }
    public int getPrice() { return this.price; }
    public String getSize() { return this.size; }

}
