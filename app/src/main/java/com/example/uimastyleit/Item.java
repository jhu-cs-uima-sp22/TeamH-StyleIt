package com.example.uimastyleit;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private User user;
    private String title;
    private String condition;
    private String description;
    private String size;
    private int price;
    private int id;

    public Item(){}

    public Item(User user, String title, String condition, String description, String size, int price) {
        this.user = user;
        this.condition = condition;
        this.description = description;
        this.title = title;
        this.size = size;
        this.price = price;
        this.id = hashCode();
    }

    protected Item(Parcel in) {
        condition = in.readString();
        description = in.readString();
        title = in.readString();
        size = in.readString();
        price = in.readInt();
        id = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getTitle() { return this.title; }
    public int getPrice() { return this.price; }
    public String getSize() { return this.size; }
    public String getCondition() { return this.condition; }
    public String getDescription() { return this.description; }
    public User getUser() { return this.user; }
    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setSize(String size) {
        this.size = size;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(condition);
        parcel.writeString(description);
        parcel.writeString(title);
        parcel.writeString(size);
        parcel.writeInt(price);
        parcel.writeInt(id);
    }
}
