package com.example.uimastyleit;

import android.hardware.display.DeviceProductInfo;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Post implements Parcelable {
    public Post(){}

    public Post(User user, String description) {
        this.user = user;
        this.description = description;
        this.postId = hashCode();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Post(Parcel in) {
        description = in.readString();
        dbId = in.readString();
        in.readList(comments, Comment.class.getClassLoader());
        postId = in.readInt();
        hasImage = in.readInt();
        in.readStringList(likes);
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
    public ArrayList<String> getLikes() {
        return likes;
    }

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public void addLike(String userID) {
        likes.add(userID);
    }
    public void dislikes(String userID) {
        likes.remove(userID);
    }
    public int numLikes(){
        return likes.size();
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getHasImage() {
        return hasImage;
    }

    public void setHasImage(int hasImage) {
        this.hasImage = hasImage;
    }

    private User user;
    private String description;
    private String dbId;
    private int postId;
    private ArrayList<Comment> comments = new ArrayList<>();
    private int hasImage = 0;
    private ArrayList<String> likes = new ArrayList<>();

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
        parcel.writeString(dbId);
        parcel.writeList(comments);
        parcel.writeInt(postId);
        parcel.writeInt(hasImage);
        parcel.writeStringList(likes);
    }
}