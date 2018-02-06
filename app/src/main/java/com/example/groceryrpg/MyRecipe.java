package com.example.groceryrpg;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Morgan on 12/21/2017.
 * Custom class to store recipes.
 */

public class MyRecipe implements Serializable {
    private String recipeName, recipeDirections;
    private Image recipeImage;
    private List<MyItem> recipeIngredients = new ArrayList<>();

    public MyRecipe (String name, Image image, List<MyItem> ingredients, String directions) {
        recipeName = name;                      recipeImage = image;
        recipeIngredients = ingredients;        recipeDirections = directions;
    }

    @Override
    public boolean equals(Object recipe) {
        return(this.recipeName.equals(((MyRecipe) recipe).recipeName) &&
            this.recipeImage.equals(((MyRecipe) recipe).recipeImage) &&
            this.recipeIngredients.equals(((MyRecipe) recipe).recipeIngredients) &&
            this.recipeDirections.equals(((MyRecipe) recipe).recipeDirections));
    }

    @Override
    public String toString() {
        return(this.recipeName+" "+this.recipeImage.toString()+" "+
                this.recipeIngredients.toString()+" "+this.recipeDirections);
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
