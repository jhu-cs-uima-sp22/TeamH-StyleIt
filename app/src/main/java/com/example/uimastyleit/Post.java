package com.example.uimastyleit;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
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

    protected Post(Parcel in) {
        description = in.readString();
        likes = in.readInt();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeInt(likes);
    }
}
