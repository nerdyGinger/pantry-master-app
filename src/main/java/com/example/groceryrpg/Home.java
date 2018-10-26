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
import android.view.View;
import android.widget.Toast;

public class Home extends AppCompatActivity implements
                                                    InventoryFrag.OnFragmentInteractionListener,
                                                    HomeFrag.OnFragmentInteractionListener,
                                                    RecipesFrag.OnFragmentInteractionListener,
                                                    ListsFrag.OnFragmentInteractionListener,
                                                    SocialFrag.OnFragmentInteractionListener {
    // This activity is the hub of the app, hosting all of the other fragments and controlling
    // navigation.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottomnav);

        Intent inIntent = getIntent();
        String page = inIntent.getStringExtra("page");
        if (page != null) {
            switch(page){
                case "Inventory":
                    loadFragment(new InventoryFrag());
                    bottomNav.setSelectedItemId(R.id.inventory);
                case "Recipes":
                    loadFragment(new RecipesFrag());
                    bottomNav.setSelectedItemId(R.id.recipes);
            }
        } else {
            loadFragment(new HomeFrag());
            bottomNav.setSelectedItemId(R.id.home);
        }

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Home":
                        loadFragment(new HomeFrag());
                        return true;
                    case "Social":
                        loadFragment(new SocialFrag());
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

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(View view) {    }

    @Override
    public void onFragmentInteraction(Uri uri) {    }
}
