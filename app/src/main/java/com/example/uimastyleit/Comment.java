package com.example.uimastyleit;

public class Comment {
    private User user;
    private String body;

    public Comment() {}

    public Comment(User user, String body) {
        this.user = user;
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
