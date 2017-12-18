package com.example.groceryrpg;

/**
 * Created by Morgan on 11/25/2017.
 * Item object
 */

public class MyItem {
    private String itemName, quantity;
    private Integer units, unitsPer;

    public MyItem() {}
    public MyItem(String itemName, String quantity, Integer units, Integer unitsPer){
        this.itemName = itemName;
        this.quantity = quantity;
        this.units = units;
        this.unitsPer = unitsPer;
    }

    public String getItemName(){ return itemName; }

    public String getQuantity(){ return quantity; }

    public Integer getUnits() { return units; }

    public Integer getUnitsPer() { return unitsPer; }

    public void setUnits(Integer units) { this.units = units; }

    public void setUnitsPer(Integer unitsPer) { this.unitsPer = unitsPer; }

    public void setItemName(String name){ this.itemName = name; }

    public void setQuantity(String num){ this.quantity = num; }
}
