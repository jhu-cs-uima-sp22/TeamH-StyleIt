package com.example.uimastyleit;

import android.os.Parcel;
import android.os.Parcelable;

public class Comment implements Parcelable {
    private User user;
    private String body;

    public Comment() {}

    public Comment(User user, String body) {
        this.user = user;
        this.body = body;
    }

    protected Comment(Parcel in) {
        body = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(body);
        parcel.writeParcelable(user, 0);
    }
}
