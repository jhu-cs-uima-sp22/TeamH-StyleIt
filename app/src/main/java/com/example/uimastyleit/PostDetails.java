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

    private FirebaseUser fUser;
    private DatabaseReference dbRef, dbPost;
    private String userID;
    User userprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        //get current User
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = fUser.getUid();
        dbPost = FirebaseDatabase.getInstance().getReference("Post");
        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userprofile = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostDetails.this, "User error!", Toast.LENGTH_SHORT).show();
            }
        });

        //get the post and user from intent
        Intent intent = getIntent();
        Post post = intent.getParcelableExtra("post");
        User user = getIntent().getParcelableExtra("user");
        ImageButton trash = findViewById(R.id.deletePost);

        if (userID.equals(user.getDbUid())) {
            trash.setVisibility(View.VISIBLE);
        }

        String name = user.getName();
        String descr = post.getDescription();

        //find and fill in information into page
        TextView detName = findViewById(R.id.detailsName);
        TextView detDesc = findViewById(R.id.detailsDescrip);
        TextView likes = findViewById(R.id.likeCounter);
        String numLikes = String.valueOf(post.getLikes());

        likes.setText(numLikes);
        detDesc.setText(descr);
        detName.setText(name);

        ImageButton like = findViewById(R.id.likeButton);
        ImageButton dislike = findViewById(R.id.dislikeButton);
        Button comment = findViewById(R.id.commentButton);
        DAOPost postDao  = new DAOPost();
        //actions for like, dislike, comment, and delete button
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hashmap = new HashMap<>();
                hashmap.put("likes", post.getLikes()+1);
                postDao.update("-N0UbHpiCTVdYqBnuASr", hashmap);
                String updatedLikes = String.valueOf(post.getLikes()+1);
                likes.setText(updatedLikes);
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hashmap = new HashMap<>();
                hashmap.put("likes", post.getLikes()-1);
                postDao.update("-N0UbHpiCTVdYqBnuASr", hashmap);
                String updatedLikes = String.valueOf(post.getLikes()-1);
                likes.setText(updatedLikes);
            }
        });
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostDetails.this, CommentPage.class);
                intent.putExtra("post", post);
                startActivity(intent);
            }
        });
        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] toDelete = new String[1];
                dbPost.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for( DataSnapshot child : snapshot.getChildren() ) {
                            if (child.equals(post)) {
                                toDelete[0] = child.getKey();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                dbPost.child(toDelete[0]).removeValue();
            }
        });

    }


}