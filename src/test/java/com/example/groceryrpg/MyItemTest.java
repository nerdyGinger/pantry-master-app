package com.example.groceryrpg;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by nerdyGinger on 10/26/18.
 * Tests functionality of MyItem class.
 */

public class MyItemTest {
    private MyItem orange = new MyItem("Orange", "4", "", 0, 0);
    private MyItem orange2 = new MyItem("Orange", "4", "", 0, 0);
    private MyItem apple = new MyItem("Apple", "2", "", 0,0);
    private MyItem flour = new MyItem("Flour", "1", "Cups", 2, 8);
    private MyItem flour2 = new MyItem("Flour", "1", "Cups", 2, 8);
    private MyItem sugar = new MyItem("Sugar", "2", "Cups", 4, 8);

    @Test
    public void test_toString() throws Exception {
        assertEquals("Orange 4  0 0", orange.toString());
        assertEquals("Apple 2  0 0", apple.toString());
        assertEquals("Flour 1 Cups 2 8", flour.toString());
        assertEquals("Sugar 2 Cups 4 8", sugar.toString());
    }

    @Test
    public void test_equals() throws Exception {
        assertTrue(orange.equals(orange2));
        assertTrue(flour.equals(flour2));
        assertFalse(orange.equals(apple));
        assertFalse(flour.equals(sugar));
    }

}