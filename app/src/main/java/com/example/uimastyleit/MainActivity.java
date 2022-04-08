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

        getSupportFragmentManager().beginTransaction().add(R.id.frag_container, new HomeFrag()).commit();
    }


    public void createPost(View view) {
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
