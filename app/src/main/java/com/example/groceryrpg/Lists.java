package com.example.groceryrpg;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class Lists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNav.setSelectedItemId(R.id.lists);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Social":
                        Toast.makeText(Lists.this, "Social", Toast.LENGTH_SHORT).show();
                        return true;
                    case "Home":
                        Intent hintent = new Intent(Lists.this, Home.class);
                        startActivity(hintent);
                        Lists.this.overridePendingTransition(0,0);
                        return true;
                    case "Recipes":
                        Intent rIntent = new Intent(Lists.this, Recipes.class);
                        startActivity(rIntent);
                        Lists.this.overridePendingTransition(0,0);
                        return true;
                    case "Inventory":
                        Intent intent = new Intent(Lists.this, Inventory.class);
                        startActivity(intent);
                        Lists.this.overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });


    }
}
