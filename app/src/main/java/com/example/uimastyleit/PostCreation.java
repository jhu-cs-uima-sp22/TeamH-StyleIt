package com.example.uimastyleit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;

public class PostCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);
        Button btn = findViewById(R.id.createPost);
        DAOPost dao = new DAOPost();

        btn.setOnClickListener(v->
        {
            User user = new User("alex", "hopkins");
            Post post = new Post(user, "This is my post");
            dao.add(post).addOnSuccessListener(suc->
            {
                Toast.makeText(this, "record is inserted", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er->
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            });
        });
    }
}