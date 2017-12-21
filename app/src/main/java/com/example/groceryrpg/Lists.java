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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Lists extends AppCompatActivity {
    private final static String prefKey = "juggleface";
    private List<String> items = new ArrayList<>();
    private List<String> units = new ArrayList<>();
    private String currentCat;
    private List<String> categories = new ArrayList<>();
    private List<MyItem> itemsList = new ArrayList<>();
    private ListsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        ItemTouchHelper ith = new ItemTouchHelper(itemTouchHelper);

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

        //get list values from sharedPreferences
        SharedPreferences preferences = getSharedPreferences(prefKey, MODE_PRIVATE);
        Integer prefSize = preferences.getAll().size();
        for (Integer i=1; i<=prefSize+1; i++) {
            String cat = preferences.getString(i.toString(), "");
            currentCat = cat;
            SharedPreferences tempPref = getSharedPreferences(cat, MODE_PRIVATE);
            Map<String, ?> itemValues = tempPref.getAll();
            Set<String> setItems = itemValues.keySet();
            String[] tempItems = (setItems.toArray(new String[setItems.size()]));
            for (String item : tempItems) {
                if (item.endsWith("1") || item.endsWith("2") || item.endsWith("3")) {
                } else {
                    categories.add(currentCat);
                    items.add(item);
                    Integer value = tempPref.getInt(item, 0);
                    Integer unitsPer = tempPref.getInt(item + "2", 0);
                    Integer currUnits = tempPref.getInt(item + "3", 0);
                    units.add(tempPref.getString(item + "1", ""));
                    MyItem temp = new MyItem(item, value.toString(), currUnits, unitsPer);
                    itemsList.add(temp);
                }
            }

        }

        //set up recycler with lists data
        final RecyclerView listsList = (RecyclerView) findViewById(R.id.listsRecycler);
        listsList.addItemDecoration(new DividerItemDecoration(
                Lists.this, LinearLayoutManager.VERTICAL));
        listsList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        listsList.setLayoutManager(llm);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Toast.makeText(Lists.this, "*Click*", Toast.LENGTH_SHORT).show();
                }
                @Override
                public boolean onLongClick(View view, int position) {
                    return true;
                }
            };
        adapter = new ListsAdapter(listener);
        adapter.updateData(itemsList);
        listsList.setAdapter(adapter);
        ith.attachToRecyclerView(listsList);
    }

    public void sendToBottom(View v) {
    }

    ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback
            (ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                    ItemTouchHelper.LEFT) {
                int toPos, dragFrom = 0;
                @Override
                public boolean isLongPressDragEnabled() {
                    return true;
                }
                @Override
                public boolean isItemViewSwipeEnabled() {
                    return false;
                }
                @Override
                public int getMovementFlags(RecyclerView rv, RecyclerView.ViewHolder vh) {
                    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                      RecyclerView.ViewHolder target) {
                    Collections.swap(adapter.mDataSet, dragFrom, toPos);
                    adapter.notifyItemMoved(dragFrom, toPos);
                    return true;
                }
                @Override
                public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
                    super.clearView(recyclerView, holder);

                }
                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) { }
            };

}
