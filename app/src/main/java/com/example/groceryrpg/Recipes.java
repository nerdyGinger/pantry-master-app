package com.example.groceryrpg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Recipes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNav.setSelectedItemId(R.id.recipes);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Social":
                        Toast.makeText(Recipes.this, "Social", Toast.LENGTH_SHORT).show();
                        return true;
                    case "Home":
                        Intent hIntent = new Intent(Recipes.this, Home.class);
                        startActivity(hIntent);
                        Recipes.this.overridePendingTransition(0,0);
                        return true;
                    case "Lists":
                        Intent lIntent = new Intent(Recipes.this, Lists.class);
                        startActivity(lIntent);
                        Recipes.this.overridePendingTransition(0,0);
                        return true;
                    case "Inventory":
                        Intent intent = new Intent(Recipes.this, Inventory.class);
                        startActivity(intent);
                        Recipes.this.overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });

        //get recipe info from SharedPreferences
        //SharedPreferences recipesPref = getSharedPreferences("AllOfMyRecipes,Yo", MODE_PRIVATE);
        RecyclerView recycler = (RecyclerView) findViewById(R.id.recipeRecycler);
        recycler.addItemDecoration(new DividerItemDecoration(
                Recipes.this, LinearLayoutManager.VERTICAL));
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(llm);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "*Click*", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(), "*Long Click*", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        RecipesAdapter adapter = new RecipesAdapter(listener);
        List<String> data = new ArrayList<>();
        data.add("PB&J");
        data.add("Lasagna");
        data.add("Cuppa Joe");
        adapter.updateData(data);
        recycler.setAdapter(adapter);
    }


    public void addRecipe(View v) {
        Intent intent = new Intent(this, AddRecipe.class);
        startActivity(intent);
    }
}
