package com.example.groceryrpg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class EditItem extends AppCompatActivity {
    private final static String prefKey = "juggleface";
    String itemName, cat, num, unit;
    EditText itemBox, quantityBox, categoryBox, unitName, unitNum, currUnits;
    Integer numUnits, numUnitsPer;
    CheckBox multiUnitCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        //get info from Inventory activity
        Intent inIntent = getIntent();
        itemName = inIntent.getStringExtra("name");
        num = inIntent.getStringExtra("number");
        cat = inIntent.getStringExtra("category");
        unit = inIntent.getStringExtra("units");
        numUnits = inIntent.getIntExtra("currUnits", 0);
        numUnitsPer = inIntent.getIntExtra("unitsPer", 0);


        //initialize all widget variables for activity
        itemBox = findViewById(R.id.itemName);
        quantityBox = findViewById(R.id.quantity);
        categoryBox = findViewById(R.id.category);
        unitName = findViewById(R.id.unitName);
        unitNum = findViewById(R.id.unitNumber);
        currUnits = findViewById(R.id.currUnit);
        multiUnitCheck = findViewById(R.id.checkMultiUnit);
        if(unit.equals("") && numUnitsPer ==0) {
            unitNum.setEnabled(false);
            unitName.setEnabled(false);
            currUnits.setEnabled(false);
        } else {
            unitName.setEnabled(true);
            unitNum.setEnabled(true);
            currUnits.setEnabled(true);
            unitName.setText(unit);
            unitNum.setText(numUnitsPer.toString());
            currUnits.setText(numUnits.toString());
        }

        itemBox.setText(itemName);
        quantityBox.setText(num);
        categoryBox.setText(cat);

        final Button saveButton = findViewById(R.id.saveButton);
        final Button deleteButton = findViewById(R.id.delete);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveItem(v);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
            }
        });
    }


    public void expandUnitMenu(View v) {
        if(multiUnitCheck.isChecked()) {
            unitName.setEnabled(true);
            unitNum.setEnabled(true);
            currUnits.setEnabled(true);
        } else {
            unitName.setEnabled(false);
            unitNum.setEnabled(false);
            currUnits.setEnabled(false);
        }
    }


    private void createNewCategory(SharedPreferences pref, String cat) {
        //saves new category to master SharedPreferences
        SharedPreferences.Editor mEditor = pref.edit();
        Integer size = pref.getAll().size() + 1;
        mEditor.putString(size.toString(), cat);
        mEditor.apply();
    }

    private void back() {
        Intent outIntent = new Intent(this, Home.class);
        outIntent.putExtra("page", "Inventory");
        startActivity(outIntent);
    }

    private void delete(View v) {
        String category = categoryBox.getText().toString();
        SharedPreferences catPref = getSharedPreferences(category, MODE_PRIVATE);
        SharedPreferences.Editor catEditor = catPref.edit();
        catEditor.remove(itemName).apply();
        SharedPreferences masterPref = getSharedPreferences(prefKey, MODE_PRIVATE);
        if (catPref.getAll().size() == 0 && masterPref.contains(category)) {
            SharedPreferences.Editor masterEditor = masterPref.edit();
            masterEditor.remove(category).apply();
        }
        back();
    }

    public void saveItem(View v) {

        //get values from widgets
        String newItem = itemBox.getText().toString();
        String strQuantity = quantityBox.getText().toString();
        String category = categoryBox.getText().toString();
        String unit = unitName.getText().toString().trim();
        String strPer = unitNum.getText().toString();
        Integer unitsPer;
        if (strPer.matches("")) {
            unitsPer = 0;
        } else {
            unitsPer = Integer.parseInt(strPer);
        }
        String strCurr = currUnits.getText().toString();
        Integer currentUnits;
        if (strCurr.matches("")) {
            currentUnits = 0;
        } else {
            currentUnits = Integer.parseInt(strCurr);
        }
        MyItem newMyItem = new MyItem(newItem, strQuantity, unit, currentUnits, unitsPer);

        //save item name to sharedPreferences
        if (newItem.matches("") || strQuantity.matches("") || category.matches("")) {
            Toast.makeText(EditItem.this, "Please enter all values", Toast.LENGTH_SHORT).show();
        } else if (multiUnitCheck.isChecked() && (unit.matches("") || unitsPer == 0)) {
            Toast.makeText(EditItem.this, "Please enter all values", Toast.LENGTH_SHORT).show();
        }else {
            //check for existing category
            SharedPreferences masterPref = getSharedPreferences(prefKey, MODE_PRIVATE);
            Integer prefSize = masterPref.getAll().size();
            List<String> categories = new ArrayList<>();
            for (Integer i = 1; i <= prefSize + 1; i++) {
                String cat = masterPref.getString(i.toString(), "");
                categories.add(cat);
            }
            if (!categories.contains(category)) {
                createNewCategory(masterPref, category);
            }

            //Remove previous item and add new item
            SharedPreferences prevCatPref = getSharedPreferences(cat, MODE_PRIVATE);
            SharedPreferences.Editor catEditor = prevCatPref.edit();
            catEditor.remove(itemName).apply();
            Gson gson = new Gson();
            String json = gson.toJson(newMyItem);
            catEditor.putString(newItem, json).apply();

            //send back to inventory list
            back();

        }

    }
}
