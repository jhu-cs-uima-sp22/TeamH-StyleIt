package com.example.uimastyleit;

public class Post {
    public Post(){}
    public Post(User user, String description) {
        this.user = user;
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private User user;
    private String description;

}
