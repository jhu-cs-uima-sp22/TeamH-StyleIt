package com.example.uimastyleit;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOPost {
    private DatabaseReference dbRef;
    public DAOPost() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbRef = db.getReference(Post.class.getSimpleName());
    }
    public Task<Void> add(Post post) {
        return dbRef.push().setValue(post);
    }
    public Task<Void> update(String key, HashMap<String, Object> hashmap) {
        return  dbRef.child(key).updateChildren(hashmap);
    }
}