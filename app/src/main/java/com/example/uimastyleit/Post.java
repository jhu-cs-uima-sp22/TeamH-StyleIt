package com.example.uimastyleit;

public class Post {
    public Post(){}

    public Post(User user, String description) {
        this.user = user;
        this.description = description;
        likes = 0;
    }

    public Post(User user, String description, int likes) {
        this.user = user;
        this.description = description;
        this.likes = likes;
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
    public int getLikes() {
        return likes;
    }
    public void addLike() {
        likes = likes+1;
    }
    public void dislikes() {likes = likes-11;}

    private User user;
    private String description;
    private int likes;

}
