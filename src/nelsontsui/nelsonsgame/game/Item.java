package nelsontsui.nelsonsgame.game;

import java.util.ArrayList;

public class Item{
    protected String name;
    protected int quantity;
    
    public Item(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }
    public String getName(){
        return name;
    }
    public int getQuantity(){
        return quantity;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public void addQuantity(int quantity){
        this.quantity+=quantity;
    }
    public void useItem(Character e){
        if(quantity>0){
            quantity--;
        }
        ability(e);
    }
    public boolean itemDepleted(){
        if(quantity<=0){
            return true;
        }
        else{
            return false;
        }
    }
    public void ability(Character e){}//override if needed
}
