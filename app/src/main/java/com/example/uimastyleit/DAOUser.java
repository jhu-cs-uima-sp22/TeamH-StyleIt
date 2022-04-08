package com.example.uimastyleit;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOUser {
    private DatabaseReference dbRef;
    public DAOUser() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbRef = db.getReference(User.class.getSimpleName());
    }
    public Task<Void> add(User user) {
        return dbRef.push().setValue(user);
    }
    public Task<Void> update(String key, HashMap<String, Object> hashmap) {
        return  dbRef.child(key).updateChildren(hashmap);
    }
}
