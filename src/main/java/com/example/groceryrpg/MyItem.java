package com.example.groceryrpg;

import java.io.Serializable;

/**
 * Created by nerdyGinger on 11/25/2017.
 * Item object
 */

public class MyItem implements Serializable {
    private String itemName, quantity, units;
    private Integer unitsCurr, unitsPer;

    public MyItem() {}
    public MyItem(String itemName, String quantity, String units, Integer unitsCurr, Integer unitsPer){
        this.itemName = itemName;
        this.quantity = quantity;
        this.units = units;
        this.unitsCurr = unitsCurr;
        this.unitsPer = unitsPer;
    }

    @Override
    public String toString() {
        return(this.itemName+" "+this.quantity+" "+this.units+" "+this.unitsCurr.toString()+" "+
            this.unitsPer.toString());
    }

    @Override
    public boolean equals(Object item) {
        return(this.itemName.equals(((MyItem) item).itemName) && this.quantity.equals(((MyItem) item).quantity) &&
            this.units.equals(((MyItem) item).units) && this.unitsCurr.equals(((MyItem) item).unitsCurr) &&
            this.unitsPer.equals(((MyItem) item).unitsPer));
    }

    public String getItemName(){ return itemName; }

    public String getQuantity(){ return quantity; }

    public String getUnits() { return units; }

    public Integer getCurrentUnits() { return unitsCurr; }

    public Integer getUnitsPer() { return unitsPer; }

    public void setUnits(String units) { this.units = units; }

    public void setUnitsPer(Integer unitsPer) { this.unitsPer = unitsPer; }

    public void setUnitsCurr(Integer unitsCurr) { this.unitsCurr = unitsCurr; }

    public void setItemName(String name){ this.itemName = name; }

    public void setQuantity(String num){ this.quantity = num; }
}
