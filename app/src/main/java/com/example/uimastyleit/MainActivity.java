package com.example.uimastyleit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
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
