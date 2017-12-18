package com.example.groceryrpg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.groceryrpg.R.id.quantity;

public class AddItem extends AppCompatActivity {
    private final static String prefKey = "juggleface";
    Intent inIntent = getIntent();
    EditText unitName, unitNum, currUnits;
    int called = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        final Button saveButton = (Button) findViewById(R.id.saveButton);
        unitName = (EditText) findViewById(R.id.unitName);
        unitNum = (EditText) findViewById(R.id.unitNumber);
        currUnits = (EditText) findViewById(R.id.currUnit);
        unitName.setEnabled(false);
        unitNum.setEnabled(false);
        currUnits.setEnabled(false);

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveItem(v);
            }
        });
    }

    public void expandUnitMenu(View v) {
        called += 1;
        if(called % 2 ==1) {
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
        Intent outIntent = new Intent(this, Inventory.class);
        startActivity(outIntent);
    }

    public void saveItem(View v) {
        //initialize all widget variables for activity
        EditText itemBox = (EditText) findViewById(R.id.itemName);
        EditText quantityBox = (EditText) findViewById(quantity);
        EditText categoryBox = (EditText) findViewById(R.id.category);

        //get values from widgets
        String newItem = itemBox.getText().toString().trim();
        String strQuantity = quantityBox.getText().toString();
        Integer quantity;
        if (strQuantity.matches("")) {
            quantity = 0;
        } else {
            quantity = Integer.parseInt(strQuantity);
        }
        String category = categoryBox.getText().toString().trim();
        String unit = unitName.getText().toString().trim();
        String strPer = unitNum.getText().toString();
        Integer unitsPer;
        if (strPer.matches("")) {
            unitsPer = 0;
        } else { unitsPer = Integer.parseInt(strPer); }
        String strCurr = currUnits.getText().toString();
        Integer currentUnits;
        if (strCurr.matches("")) {
            currentUnits = 0;
        } else { currentUnits = Integer.parseInt(strCurr); }

        //save item name to sharedPreferences
        if (newItem.matches("") || quantity == 0 || category.matches("")) {
            Toast.makeText(AddItem.this, "Please enter all values", Toast.LENGTH_SHORT).show();
        } else {
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
            SharedPreferences catPref = getSharedPreferences(category, MODE_PRIVATE);
            if(catPref.contains(newItem)) {
                Toast.makeText(AddItem.this, newItem+" already in "+category, Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor catEditor = catPref.edit();
                catEditor.putInt(newItem, quantity).apply();
                catEditor.putString(newItem+"1", unit).apply();
                catEditor.putInt(newItem+"2", unitsPer).apply();
                catEditor.putInt(newItem+"3", currentUnits).apply();

                //send back to inventory list
                back();
            }
        }


    }
}
