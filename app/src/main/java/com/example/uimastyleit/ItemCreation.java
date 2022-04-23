package com.example.uimastyleit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemCreation extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String userID;
    User userprofile;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_creation2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        EditText postDesc = findViewById(R.id.createItemDescr);
        EditText itemName = findViewById(R.id.createitemName);
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
            dao.add(item).addOnSuccessListener(suc->
            {
                Toast.makeText(this, "Item Created!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, HomeFrag.class);
//                startActivity(intent);
            }).addOnFailureListener(er->
                    Toast.makeText(this, "Error, item not created!", Toast.LENGTH_SHORT).show());
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

                    return true;
            }
            return false;
        });

        imageButton.setOnClickListener(v -> dropDownMenu.show());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }
}
