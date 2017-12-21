package com.example.groceryrpg;

import android.media.Image;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morgan on 12/21/2017.
 * Custom class to store recipes.
 */

public class MyRecipe {
    private String recipeName, recipeDirections;
    private Image recipeImage;
    private List<MyItem> recipeIngredients = new ArrayList<>();

    public MyRecipe (String name, Image image, List<MyItem> ingredients, String directions) {
        recipeName = name;                      recipeImage = image;
        recipeIngredients = ingredients;        recipeDirections = directions;
    }

    public String getRecipeName() { return recipeName; }

    public void setRecipeName(String name) { recipeName = name; }

    public Image getRecipeImage() { return recipeImage; }

    public void setRecipeImage(Image image) { recipeImage = image; }

    public List<MyItem> getRecipeIngredients() { return recipeIngredients; }

    public void setRecipeIngredients(List<MyItem> items) { recipeIngredients = items; }

    public String getRecipeDirections() { return recipeDirections; }

    public void setRecipeDirections(String directions) { recipeDirections = directions; }
}
