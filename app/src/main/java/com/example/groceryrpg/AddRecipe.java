package com.example.groceryrpg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddRecipe extends AppCompatActivity {
    private TextView ingredientsBox;
    private List<MyItem> ingredients = new ArrayList<>();
    private Integer num = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        ingredientsBox = (TextView) findViewById(R.id.ingredientsList);
    }

    public void addIngredient(View v) {
        //------> popup fragment here later

        //Add ingredient to global list
        MyItem peanutButter = new MyItem("Peanut Butter", "1", "Tbsp", 12, 18);
        ingredients.add(peanutButter);

        //set ingredients text
        String text = "";
        for(MyItem i : ingredients) {
            text = text.concat(i.getItemName()+"\t-\t\t\t\t"+num.toString()+"\t"+i.getUnits()+"\n");
        }
        ingredientsBox.setText(text);
    }

    public void back(View v) {
        //initialize widgets
        EditText nameBox = (EditText) findViewById(R.id.newRecipeName);
        //ImageButton imageBox;
        EditText directionsBox = (EditText) findViewById(R.id.directions);

        //get info
        String newName = nameBox.getText().toString();
        Image image = null;
        String ingredientsString = ingredients.toString();
        String newDirections = directionsBox.getText().toString();
        MyRecipe newRecipe = new MyRecipe(newName, image, ingredients, newDirections);

        if(!newName.equals("") && !ingredientsString.equals("\n\n\n") && !newDirections.equals("\t(Tap to edit)\n\n")) {
            //save recipe info
            SharedPreferences recipesPref = getSharedPreferences("AllOfMyRecipes,Yo", MODE_PRIVATE);
            SharedPreferences.Editor recipesEditor = recipesPref.edit();
            Gson gson = new Gson();
            String json = gson.toJson(newRecipe);
            recipesEditor.putString(newName, json);
            recipesEditor.apply();

            //File to delete: "MyRecipes,Yo!.txt"


            //Send back to Recipes activity
            Intent intent = new Intent(AddRecipe.this, Recipes.class);
            startActivity(intent);
        }
    }
}
