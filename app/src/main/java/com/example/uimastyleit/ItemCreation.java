package com.example.uimastyleit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ItemCreation extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String userID;
    User userprofile;
    ImageView itemCreationImage;
    Uri uri;
    private StorageReference storageReference;
    boolean photoAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_creation2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        EditText postDesc = findViewById(R.id.createItemDescr);
        EditText itemName = findViewById(R.id.createitemName);
        itemCreationImage = findViewById(R.id.itemCreationImage);
        EditText itemCond = findViewById(R.id.createitemCondition);
        EditText itemSize = findViewById(R.id.createitemSize);
        EditText itemPrice = findViewById(R.id.createItemPrice);
        Button btn = findViewById(R.id.createItem);
        DAOItem dao = new DAOItem();
        dbRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userprofile = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ItemCreation.this, "User error!", Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(v->
        {
            String postDescription = postDesc.getText().toString().trim();
            String toCreateName = itemName.getText().toString().trim();
            String toCreatePrice = itemPrice.getText().toString().trim();
            String toCreateSize = itemSize.getText().toString().trim();
            String toCreateCondition = itemCond.getText().toString().trim();
            Item item = new Item(userprofile, toCreateName, toCreateCondition, postDescription, toCreateSize, Integer.parseInt(toCreatePrice));
            if (photoAdded) {
                uploadImagetoFirebase(uri, item.getId());
                item.setHasImage(1);
            }
            dao.add(item).addOnSuccessListener(suc->
            {
                Toast.makeText(this, "Item Created!", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(er->
                    Toast.makeText(this, "Error, item not created!", Toast.LENGTH_SHORT).show());
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
        System.out.println(userprofile.getName());
        if (requestCode == 1) {
            if (data != null) {
                uri = data.getData();
                photoAdded = true;
                itemCreationImage.setImageURI(uri);
            } else {
                Toast.makeText(this, "No image Selected", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        }
    }

    private void uploadImagetoFirebase(Uri uri, int id) {
        StorageReference fileRef = storageReference.child("items/"+ id +"/itemImage.jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ItemCreation.this, "Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ItemCreation.this, "Failed to upload", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
