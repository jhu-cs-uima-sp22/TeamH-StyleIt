package com.example.uimastyleit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ItemDetails extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String userID;
    private String sellerEmail;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details2);

        Item item = getIntent().getParcelableExtra("item");
        User user = getIntent().getParcelableExtra("userItem");
        String name = user.getName();
        sellerEmail = user.getEmail();
        title = item.getTitle();
        String descr = item.getDescription();
        String size = item.getSize();
        String condition = item.getCondition();
        int price = item.getPrice();

        TextView iTitle = findViewById(R.id.itemDetailTitle);
        TextView iDesc = findViewById(R.id.itemDetailDescr);
        TextView iCond = findViewById(R.id.itemDetailCondition);
        TextView iPrice = findViewById(R.id.itemDetailPrice);
        TextView iSeller = findViewById(R.id.itemDetailSeller);
        TextView iSize = findViewById(R.id.itemDetailSize);
        ImageView image = findViewById(R.id.itemDetailImage);

        iTitle.setText(title);
        iDesc.setText(descr);
        iCond.setText("Condition: "+ condition);
        iPrice.setText("Price: $"+ String.valueOf(price));
        iSeller.setText(name);
        iSize.setText("Size: "+ size);
        Button buy = findViewById(R.id.buyButton);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("items/"+item.getId()+"/itemImage.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(image);
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });
    }

    public void sendMail() {
        String[] recipient = {sellerEmail};
        String subject = "Style-It Item: " + title;
        String message = "Hello,\n\n I'm interested in buying your item: " + title + ".";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipient);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}
