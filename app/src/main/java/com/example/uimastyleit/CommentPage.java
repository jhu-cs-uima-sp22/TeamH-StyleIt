package com.example.uimastyleit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentPage extends AppCompatActivity implements MyAdapterComment.OnPostListener {
    private FirebaseUser user;
    RecyclerView recyclerView;
    DatabaseReference dbRef;
    MyAdapterComment myAdapterComment;
    ArrayList<Comment> commentList;
    private String userID;
    User userprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_page);

        Post post = getIntent().getParcelableExtra("post");
        TextView commentBody = findViewById(R.id.commentBody);

        recyclerView = findViewById(R.id.CommentList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CommentPage.this));
        commentList = post.getComments();
        myAdapterComment = new MyAdapterComment(CommentPage.this, commentList, this);
        recyclerView.setAdapter(myAdapterComment);

        myAdapterComment.notifyDataSetChanged();

        DAOPost postDao  = new DAOPost();
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        Button btn = findViewById(R.id.postButton);
        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userprofile = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CommentPage.this, "User error!", Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(v->
        {
            String commentText = commentBody.getText().toString().trim();
            Comment comment = new Comment(userprofile, commentText);
            HashMap<String, Object> hashmap = new HashMap<>();
            ArrayList<Comment> addedCom = post.getComments();
            addedCom.add(comment);
            hashmap.put("comments", addedCom);
            postDao.update(post.getDbId(), hashmap);
            Toast.makeText(CommentPage.this, "Comment Added!", Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public void onPostClick(int position) {

    }
}