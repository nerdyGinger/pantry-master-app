package com.example.groceryrpg;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by nerdyGinger on 3/6/18.
 * Dialog window to add ingredients to a new recipe.
 * Most recent edit: 5/8/18
 */

public class IngredientDialog extends DialogFragment {
    TextView unitsBox;
    AutoCompleteTextView itemName;
    EditText quantity;
    Button cancelButton, saveButton;
    List<MyItem> itemsList = new ArrayList<>();
    List<String> itemNames = new ArrayList<>();
    List<String> cats = new ArrayList<>();

    //Host activities need to implement this interface!
    public interface DialogListener {
        void onDialogPositiveClick(MyItem item, String num);
        void onDialogNegativeClick();
    }

    DialogListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //layout file, load item data, initialize widgets
        View rootView = inflater.inflate(R.layout.fragment_ingredient_dialog, container, false);
        loadItems();
        itemName = rootView.findViewById(R.id.autoIngredient);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, itemNames);
            itemName.setAdapter(adapter);
            itemName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int num, long numTwo) {
                    MyItem item = getItem(itemName.getText().toString());
                    unitsBox.setText(item.getUnits());
                }
            });
        unitsBox = rootView.findViewById(R.id.ingredientUnit);
        quantity = rootView.findViewById(R.id.ingredientNum);
        cancelButton = rootView.findViewById(R.id.cancelIngredient);
        saveButton = rootView.findViewById(R.id.saveIngredient);

        //setting up widgets to collect user inputs
        mListener = (DialogListener) getActivity();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //negative click action
                mListener.onDialogNegativeClick();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //positive click action
                MyItem item = getItem(itemName.getText().toString());
                mListener.onDialogPositiveClick(item, quantity.getText().toString());
            }
        });

        return rootView;
    }

    private void loadItems () {
        //pulls item data from SharedPreferences and updates the List itemsList
        SharedPreferences masterPref = this.getActivity().getSharedPreferences("juggleface", Context.MODE_PRIVATE);
        Set<String> listCats = masterPref.getAll().keySet();
        for(String i : listCats) {
            String cat = masterPref.getString(i, "");
            SharedPreferences tempPref = this.getActivity().getSharedPreferences(cat, Context.MODE_PRIVATE);
            Set<String> tempItemNames = tempPref.getAll().keySet();
            for(String j : tempItemNames) {
                Gson gson = new Gson();
                String json = tempPref.getString(j, "");
                itemsList.add(gson.fromJson(json, MyItem.class));
                cats.add(cat);
                itemNames.add(j);
            }
        }
    }

    private MyItem getItem (String itemName) {
        //finds input item itemsList and returns MyItem data type, or returns new MyItem if not found
        for(MyItem i : itemsList) {
            if(i.getItemName().equals(itemName)) {
                return i;
            }
        }
        return new MyItem(itemName, "0", "N/A", 0, 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement my cool DialogListener interface :P");
        }
    }

}
