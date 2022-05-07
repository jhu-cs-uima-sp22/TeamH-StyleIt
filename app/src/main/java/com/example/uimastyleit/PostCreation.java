package com.example.uimastyleit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class PostCreation extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String userID;
    User userprofile;
    ImageView postCreationImage;
    Uri uri;
    boolean photoAdded = false;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        EditText postDesc = findViewById(R.id.createPostDescr);
        Button btn = findViewById(R.id.createPost);
        DAOPost dao = new DAOPost();
        postCreationImage = findViewById(R.id.postCreationImage);
        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userprofile = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostCreation.this, "User error!", Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(v->
        {
            String postDescription = postDesc.getText().toString().trim();
            Post post = new Post(userprofile, postDescription);
            if(photoAdded) {
                uploadImagetoFirebase(uri, post.getPostId());
                post.setHasImage(true);
            }
            dao.add(post).addOnSuccessListener(suc->
            {
                Toast.makeText(this, "Post Created!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }).addOnFailureListener(er->
                    Toast.makeText(this, "Error, post not created!", Toast.LENGTH_SHORT).show());
        });

        final ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        final PopupMenu dropDownMenu = new PopupMenu(this, imageButton);
        final Menu menu = dropDownMenu.getMenu();

        menu.add(0, 0, 0, "Choose from gallery");

        dropDownMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult ( intent, 1 );
                    return true;
            }
            return false;
        });

        imageButton.setOnClickListener(v -> dropDownMenu.show());

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            uri = data.getData();
            photoAdded = true;
            postCreationImage.setImageURI(uri);
        }
    }

    private void uploadImagetoFirebase(Uri uri, int id) {
        StorageReference fileRef = storageReference.child("posts/"+ id+"/postImage.jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PostCreation.this, "Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostCreation.this, "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        });
    }

}