package com.example.uimastyleit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class ItemDetails extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference dbRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details2);

        Item item = getIntent().getParcelableExtra("item");
        User user = getIntent().getParcelableExtra("userItem");
        String name = user.getName();
        String title = item.getTitle();
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

        iTitle.setText(title);
        iDesc.setText(descr);
        iCond.setText(condition);
        iPrice.setText(price);
        iSeller.setText(name);
        iSize.setText(size);

    }
}