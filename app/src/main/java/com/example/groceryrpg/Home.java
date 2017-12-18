package com.example.groceryrpg;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNav.setSelectedItemId(R.id.home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Social":
                        Toast.makeText(Home.this, "Social", Toast.LENGTH_SHORT).show();
                        return true;
                    case "Recipes":
                        Intent rintent = new Intent(Home.this, Recipes.class);
                        startActivity(rintent);
                        Home.this.overridePendingTransition(0,0);
                        return true;
                    case "Lists":
                        Intent lIntent = new Intent(Home.this, Lists.class);
                        startActivity(lIntent);
                        Home.this.overridePendingTransition(0,0);
                        return true;
                    case "Inventory":
                        Intent intent = new Intent(Home.this, Inventory.class);
                        startActivity(intent);
                        Home.this.overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });
    }
}
