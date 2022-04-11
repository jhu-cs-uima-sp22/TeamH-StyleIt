package com.example.uimastyleit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class PostDetails extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
//        user = FirebaseAuth.getInstance().getCurrentUser();
//        dbRef = FirebaseDatabase.getInstance().getReference("Users");
//        userID = user.getUid();
        Post post = getIntent().getParcelableExtra("post");
        User user = getIntent().getParcelableExtra("user");
        String name = user.getName();
        String descr = post.getDescription();
        TextView detName = findViewById(R.id.detailsName);
        TextView detDesc = findViewById(R.id.detailsDescrip);

        detDesc.setText(descr);
        detName.setText(name);

        ImageButton like = findViewById(R.id.likeButton);
        DAOPost postDao  = new DAOPost();
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hashmap = new HashMap<>();
                hashmap.put("likes", post.getLikes()+1);
                postDao.update("-N-KpHQcSMTgBa7n-U1H", hashmap);
            }
        });

        TextView likes = findViewById(R.id.likeCounter);
        String numLikes = String.valueOf(post.getLikes());
        likes.setText(numLikes);



//        ImageButton likeBut = findViewById(R.id.likeButton);
//        ImageButton dislikeBut = findViewById(R.id.dislikeButton);
//        DAOPost postDao  = new DAOPost();
//        likeBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                HashMap<String, Object> hashmap = new HashMap<>();
//                hashmap.put("likes", post.getLikes()+1);
//                postDao.update("-N-KpHQcSMTgBa7n-U1H", hashmap);
//                post.addLike();
//
//            }
//        });
//
//        dislikeBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                post.dislikes();
//            }
//        });
        //getActionBar().setTitle("Post Details");
    }




}