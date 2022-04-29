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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class PostCreation extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String userID;
    User userprofile;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_creation);
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        EditText postDesc = findViewById(R.id.createPostDescr);
        Button btn = findViewById(R.id.createPost);
        DAOPost dao = new DAOPost();
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
            dao.add(post).addOnSuccessListener(suc->
            {
                Toast.makeText(this, "Post Created!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, HomeFrag.class);
//                startActivity(intent);
            }).addOnFailureListener(er->
                    Toast.makeText(this, "Error, post not created!", Toast.LENGTH_SHORT).show());
        });

        final ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        final PopupMenu dropDownMenu = new PopupMenu(this, imageButton);
        final Menu menu = dropDownMenu.getMenu();

        menu.add(0, 0, 0, "Take picture");
        menu.add(0, 1, 0, "Choose from gallery");

        dropDownMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case 0:
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 0);
                    return true;
                case 1:
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult ( intent, 1 );
                    return true;
            }
            return false;
        });

        imageButton.setOnClickListener(v -> dropDownMenu.show());

    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//        imageView.setImageBitmap(bitmap);
//    }

//    //@Override
//    @SuppressLint("MissingSuperCall")
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        System.out.println("hi");
//        //super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//            Bitmap image = (Bitmap) data.getExtras().get("data");
//            imageView2.setImageBitmap(image);
//            //userprofile.setImage(image);
//            //System.out.println(userprofile.getImage());
//            System.out.println("saved image");
//
//
//        } else if (requestCode == 1) {
//
//            System.out.println("Printing for gallery");
//            try {
//                Uri uri = data.getData();
//                InputStream inputStream;
//                inputStream = getContentResolver().openInputStream(uri);
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                //userprofile.setImage(bitmap);
//                imageView2.setImageBitmap(bitmap);
//            } catch (FileNotFoundException e) {
//                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
//            }
//        }
//    }



}