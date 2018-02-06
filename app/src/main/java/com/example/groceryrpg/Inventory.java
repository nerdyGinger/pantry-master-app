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
import java.util.Map;
import java.util.Set;

public class Inventory extends AppCompatActivity{
    private final static String prefKey = "juggleface";
    private List<String> items = new ArrayList<>();
    private String currentCat;
    private List<String> categories = new ArrayList<>();
    private List<MyItem> itemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        //set up bottom navigation
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottomnav);
        bottomNav.setSelectedItemId(R.id.inventory);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.toString()) {
                    case "Social":
                        Toast.makeText(Inventory.this, "Social", Toast.LENGTH_SHORT).show();
                        return true;
                    case "Recipes":
                        Intent rIntent = new Intent(Inventory.this, Recipes.class);
                        startActivity(rIntent);
                        Inventory.this.overridePendingTransition(0,0);
                        return true;
                    case "Lists":
                        Intent lIntent = new Intent(Inventory.this, Lists.class);
                        startActivity(lIntent);
                        Inventory.this.overridePendingTransition(0,0);
                        return true;
                    case "Home":
                        Intent intent = new Intent(Inventory.this, Home.class);
                        startActivity(intent);
                        Inventory.this.overridePendingTransition(0,0);
                        return true;
                }
                return true;
            }
        });

        //get list values from sharedPreferences
        SharedPreferences preferences = getSharedPreferences(prefKey, MODE_PRIVATE);
        Integer prefSize = preferences.getAll().size();
        for (Integer i=1; i<=prefSize+1; i++) {
            String cat = preferences.getString(i.toString(), "");
            currentCat = cat;
            SharedPreferences tempPref = getSharedPreferences(cat, MODE_PRIVATE);
            Map<String, ?> itemValues = tempPref.getAll();
            Set<String> setItems = itemValues.keySet();
            String [] tempItems = (setItems.toArray(new String[setItems.size()]));
            for (String item : tempItems) {
                if (!item.endsWith("1") && !item.endsWith("2") && !item.endsWith("3")) {
                    categories.add(currentCat);
                    items.add(item);
                    Integer value = tempPref.getInt(item, 0);
                    Integer unitsPer = tempPref.getInt(item + "2", 0);
                    Integer currUnits = tempPref.getInt(item + "3", 0);
                    String unit = (tempPref.getString(item + "1", ""));
                    MyItem temp = new MyItem(item, value.toString(), unit, currUnits, unitsPer);
                    itemsList.add(temp);
                    }
                }
            }

        //set RecyclerView with saved values
        final RecyclerView inventoryList = (RecyclerView) findViewById(R.id.recycler);
        inventoryList.addItemDecoration(new DividerItemDecoration(
                Inventory.this, LinearLayoutManager.VERTICAL));
        inventoryList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        inventoryList.setLayoutManager(llm);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                editInventory(position);
            }

            @Override
            public boolean onLongClick(View view, int position) {
                return true;
            }
        };
        InventoryAdapter mAdapter = new InventoryAdapter(listener);
        mAdapter.updateData(itemsList);
        inventoryList.setAdapter(mAdapter);

    }

    public void editInventory(int position) {
        Intent intent = new Intent( this, EditItem.class);
        MyItem choosen = itemsList.get(position);
        String itemName = choosen.getItemName();
        String num = choosen.getQuantity();
        String cat = categories.get(position);
        String unit = choosen.getUnits();
        Integer unitsPer = choosen.getUnitsPer();
        Integer current = choosen.getCurrentUnits();

        intent.putExtra("name", itemName);
        intent.putExtra("number", num);
        intent.putExtra("category", cat);
        intent.putExtra("units", unit);
        intent.putExtra("unitsPer", unitsPer);
        intent.putExtra("currUnits", current);
        startActivity(intent);
        }

    public void addToInventory(View v) {
        Intent intent = new Intent(this, AddItem.class);
        startActivity(intent);
    }


}


