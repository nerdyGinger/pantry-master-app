package com.example.groceryrpg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EditRecipe extends AppCompatActivity implements IngredientDialog.DialogListener {
    EditText nameBox, directionsBox;
    TextView ingredientsBox;
    IngredientDialog dialog;
    List<MyRecipe> recipes = new ArrayList<>();
    List<String> recipeNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        loadRecipes();

        Intent inIntent = getIntent();
        String recipeName = inIntent.getStringExtra("recipeName");
        MyRecipe inRecipe = getRecipe(recipeName);

        //initialize widgets
        nameBox = findViewById(R.id.edit_NewRecipeName);
            nameBox.setText(recipeName);
        directionsBox = findViewById(R.id.edit_Directions);
            directionsBox.setText(inRecipe.getRecipeDirections());
        ingredientsBox = findViewById(R.id.edit_IngredientsList);
            String text = "";
            for(MyItem i : inRecipe.getRecipeIngredients()) {
                text = text.concat(i.getItemName()+"\t -\t\t\t\t"+"3RR0R"+"\t"+i.getUnits()+"\n");
            }
            ingredientsBox.setText(text);


    }

    private void loadRecipes() {
        SharedPreferences recipesPref = getSharedPreferences("AllOfMyRecipes,Yo", Context.MODE_PRIVATE);
        Set<String> keys = recipesPref.getAll().keySet();
        recipes = new ArrayList<>(); // TODO: update RecipesAdapter to better represent recipe data
        recipeNames = new ArrayList<>(); // ...until then, we'll just use the names
        for(String i : keys) {
            Gson gson = new Gson();
            String json = recipesPref.getString(i, "");
            MyRecipe temp = gson.fromJson(json, MyRecipe.class);
            recipes.add(temp); recipeNames.add(temp.getRecipeName());
        }
    }

    private MyRecipe getRecipe (String recipeName) {
        //finds input item itemsList and returns MyItem data type, or returns new MyItem if not found
        for (MyRecipe i : recipes) {
            if (i.getRecipeName().equals(recipeName)) {
                return i;
            }
        }
        return new MyRecipe(recipeName, null, new ArrayList<MyItem>(), " ");
    }

    public void showDialog(View v) {
        dialog = new IngredientDialog();
        dialog.show(getSupportFragmentManager(), "AddIngredient");
    }

    public void onDialogPositiveClick(MyItem item, String quantity) {

    }

    public void onDialogNegativeClick() {
        dialog.dismiss();
    }

}
