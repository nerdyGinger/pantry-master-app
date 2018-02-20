package com.example.groceryrpg;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class Home extends AppCompatActivity implements
                                                    InventoryFrag.OnFragmentInteractionListener,
                                                    HomeFrag.OnFragmentInteractionListener,
                                                    RecipesFrag.OnFragmentInteractionListener,
                                                    ListsFrag.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loadFragment(new HomeFrag());

        BottomNavigationView bottomNav = findViewById(R.id.bottomnav);
        bottomNav.setSelectedItemId(R.id.home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Home":
                        loadFragment(new HomeFrag());
                        return true;
                    case "Social":
                        Toast.makeText(Home.this, "Social", Toast.LENGTH_SHORT).show();
                        return true;
                    case "Recipes":
                        loadFragment(new RecipesFrag());
                        return true;
                    case "Lists":
                        loadFragment(new ListsFrag());
                        return true;
                    case "Inventory":
                        loadFragment(new InventoryFrag());
                        return true;
                }
                return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {    }
}
