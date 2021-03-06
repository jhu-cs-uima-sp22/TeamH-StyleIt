package com.example.uimastyleit;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseAuth;

public class User implements Parcelable {
    private String password;
    private String name;
    private String email;
    private Bitmap image;
    private String dbUid;
    private int id;
    public String getDbUid() {
        return dbUid;
    }

    public void setDbUid(String dbUid) {
        this.dbUid = dbUid;
    }

    public User(){}

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.id = hashCode();
    }

    protected User(Parcel in) {
        password = in.readString();
        name = in.readString();
        email = in.readString();
        id = in.readInt();
        dbUid = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImage(Bitmap a) {
        image = a;
    }

    public Bitmap getImage() {
        return image;
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(password);
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeInt(id);
        parcel.writeString(dbUid);
    }
}
