package com.example.uimastyleit;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAOItem {
    private DatabaseReference dbRef;
    public DAOItem() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbRef = db.getReference(Item.class.getSimpleName());
    }
    public Task<Void> add(Item item) {
        return dbRef.push().setValue(item);
    }
    public Task<Void> update(String key, HashMap<String, Object> hashmap) {
        return  dbRef.child(key).updateChildren(hashmap);
    }
}