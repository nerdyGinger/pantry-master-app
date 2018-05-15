package com.example.groceryrpg;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class AddRecipe extends AppCompatActivity implements IngredientDialog.DialogListener {
    private TextView ingredientsBox;
    private List<MyItem> ingredients = new ArrayList<>();
    private List<String> quantities = new ArrayList<>();
    IngredientDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        ingredientsBox = findViewById(R.id.ingredientsList);
    }

    public void onDialogNegativeClick() {
        // action when cancel button is clicked:
        dialog.dismiss();
    }

    public void onDialogPositiveClick(MyItem item, String quantity) {
        // action when add button is clicked:
        //  - add ingredient and quantity to global lists
        ingredients.add(item);
        quantities.add(quantity);

        //  - set ingredients text from global list
        String text = "";
        for(int i=0; i<ingredients.size(); i++) {
            MyItem temp = ingredients.get(i);
            text = text.concat(temp.getItemName()+"\t -\t\t\t\t"+quantities.get(i)+"\t"+temp.getUnits()+"\n");
        }
        ingredientsBox.setText(text);

        //  - dismiss dialog
        dialog.dismiss();
    }

    public void addIngredient(View v) {
        //call up ingredient dialog
        dialog = new IngredientDialog();
        dialog.show(getSupportFragmentManager(),"AddIngredient");
    }


    public void back(View v) {
        //initialize widgets
        EditText nameBox = findViewById(R.id.newRecipeName);
        //ImageButton imageBox;
        EditText directionsBox = findViewById(R.id.directions);

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

        }
        //Send back to Recipes activity
        Intent intent = new Intent(AddRecipe.this, Home.class);
        intent.putExtra("page", "Recipes");
        startActivity(intent);
    }
}
