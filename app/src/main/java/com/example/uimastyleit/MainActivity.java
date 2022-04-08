package com.example.uimastyleit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        EditText name = findViewById(R.id.editText2);
        EditText password = findViewById(R.id.editText1);
        Button btn = findViewById(R.id.button1);
        DAOUser dao  = new DAOUser();

        btn.setOnClickListener(v->
        {
            User user = new User(name.getText().toString(), password.getText().toString());
//            dao.add(user).addOnSuccessListener(suc->
//            {
//                Toast.makeText(this, "record is inserted", Toast.LENGTH_SHORT).show();
//            }).addOnFailureListener(er->
//            {
//                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//            });
            HashMap<String, Object> hashmap = new HashMap<>();
            hashmap.put("name", name.getText().toString());
            hashmap.put("password", password.getText().toString());
            dao.update("-N-6DVAE83tOZco_urX3", hashmap).addOnSuccessListener(suc->
            {
                Toast.makeText(this, "record is inserted", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(er->
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            });
        });

    }

    public void createPost(View view) {
        Intent intent = new Intent(this, PostCreation.class);
        startActivity(intent);
    }

    public void registerPage(View view) {
        Intent intent = new Intent(this, PostCreation.class);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFrag = null;
            switch (item.getItemId()) {
                case R.id.home:
                    selectedFrag = new HomeFrag();
                    break;
                case R.id.shopping_cart:
                    selectedFrag = new MarketplaceFrag();
                    break;
                case R.id.search:
                    selectedFrag = new SearchFrag();
                    break;
                case R.id.profile:
                    selectedFrag = new ProfileFrag();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, selectedFrag).commit();
            return true;
        }
    };


    // ALEX EDIT
    // to pull the master branch: git pull origin master --allow-unrelated-histories
}
