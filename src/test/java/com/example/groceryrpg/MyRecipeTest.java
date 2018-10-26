package com.example.groceryrpg;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/*
 * Created by nerdyGinger on 10/26/18.
 * Tests functionality of MyRecipe class.
 */

public class MyRecipeTest {
    private MyItem peanutButter = new MyItem("Peanut Butter", "1", "Tbs", 8, 12);
    private MyItem jelly = new MyItem("Jelly", "1", "Tbs", 6, 12);
    private MyItem milk = new MyItem("Milk", "2", "Cups", 4, 8);
    private MyItem apple = new MyItem("Apple", "6", "", 0,0);
    private List<MyItem> ingr = new ArrayList<>();

    @Test
    public void test_toString() throws Exception {
        ingr.add(peanutButter);
        ingr.add(jelly);
        MyRecipe pbAndJ = new MyRecipe("PB & J", null, ingr, "Put them tog.");
        assertEquals("PB & J "+ingr.toString()+" Put them tog.", pbAndJ.toString());

        ingr.add(milk);
        MyRecipe gross = new MyRecipe("Nastiness", null, ingr, "Mix it up.");
        assertEquals("Nastiness "+ingr.toString()+" Mix it up.", gross.toString());

        ingr.add(apple);
        MyRecipe eugh = new MyRecipe("Schnasty!", null, ingr, "Doesn't even matter.");
        assertEquals("Schnasty! "+ingr.toString()+" Doesn't even matter.", eugh.toString());
    }

    @Test
    public void test_equals() throws Exception {
        MyRecipe pbAndJ = new MyRecipe("PB & J", null, ingr, "Put them tog.");
        MyRecipe gross = new MyRecipe("Nastiness", null, ingr, "Mix it up.");
        MyRecipe eugh = new MyRecipe("Schnasty!", null, ingr, "Doesn't even matter.");

        assertTrue(pbAndJ.equals(pbAndJ));
        assertFalse(pbAndJ.equals(gross));
        assertFalse(gross.equals(eugh));
        assertFalse(eugh.equals(pbAndJ));
    }
}
