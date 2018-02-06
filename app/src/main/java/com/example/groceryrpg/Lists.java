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
import android.util.Log;
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
    private List<MyItem> itemsList = new ArrayList<>();
    private List<MyItem> checkList = new ArrayList<>();
    private List<MyItem> toBeDropped = new ArrayList<>();
    private List<MyItem> toBeRaised = new ArrayList<>();
    private List<MyItem> currentList = new ArrayList<>();
    private ListsAdapter adapter;
    private CheckBox check;

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

        //get lists data
        MyItem apples = new MyItem("Apples", "2", "", 0, 0);
        MyItem oranges = new MyItem("Oranges", "3", "", 0,0);
        MyItem bananas = new MyItem("Bananas", "1", "", 0, 0);
        MyItem dates = new MyItem("Dates", "4", "", 0, 0);
        itemsList.add(apples);
        itemsList.add(oranges);
        itemsList.add(bananas);
        checkList.add(dates);
        currentList.addAll(itemsList);
        currentList.addAll(checkList);

        //set up recycler with lists data
        final RecyclerView listsList = (RecyclerView) findViewById(R.id.listsRecycler);
        listsList.addItemDecoration(new DividerItemDecoration(
                Lists.this, LinearLayoutManager.VERTICAL));
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
        adapter.updateData(itemsList, checkList);
        listsList.setAdapter(adapter);
        ith.attachToRecyclerView(listsList);
    }

    public void crossOut(View v) {
        check = (CheckBox)v;
        View parentRow = (View) v.getParent();
        RecyclerView rv = (RecyclerView) parentRow.getParent();
        int position = rv.getChildAdapterPosition(parentRow);
        MyItem i = currentList.get(position);

        if(check.isChecked()) {
            if(toBeRaised.contains(i)) {
                toBeRaised.remove(i);
            }
            toBeDropped.add(i);
        } else {
            if(toBeDropped.contains(i)) {
                toBeDropped.remove(i);
            }
            toBeRaised.add(i);
        }
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
