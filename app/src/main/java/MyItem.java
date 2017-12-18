/**
 * Created by Morgan on 11/25/2017.
 * Item object
 */

public class MyItem{
    private String itemName, quantity;

    public MyItem() {}

    public MyItem(String itemName, String quantity){
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public String getItemName(){ return itemName; }

    public String getQuantity(){ return quantity; }

    public void setItemName(String name){ this.itemName = name; }

    public void setQuantity(String num){ this.quantity = num; }
}
