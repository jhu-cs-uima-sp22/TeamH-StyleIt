package com.example.uimastyleit;

import android.hardware.display.DeviceProductInfo;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Post implements Parcelable {
    public Post(){}

    public Post(User user, String description) {
        this.user = user;
        this.description = description;
        likes = 0;
        this.postId = hashCode();
    }

    public Post(User user, String description, int likes) {
        this.user = user;
        this.description = description;
        this.likes = likes;
        this.postId = hashCode();
    }

    protected Post(Parcel in) {
        description = in.readString();
        likes = in.readInt();
        dbId = in.readString();
        in.readList(comments, Comment.class.getClassLoader());
        postId = in.readInt();
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

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public void addLike() {
        likes = likes+1;
    }
    public void dislikes() {likes = likes-1;}

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    private User user;
    private String description;
    private int likes;
    private String dbId;
    private int postId;
    private ArrayList<Comment> comments = new ArrayList<>();

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
        parcel.writeInt(likes);
        parcel.writeString(dbId);
        parcel.writeList(comments);
        parcel.writeInt(postId);
    }
}