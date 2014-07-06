package nelsontsui.nelsonsgame.game;

import java.util.ArrayList;
import java.io.Serializable;

public class Inventory implements Serializable{
    protected ArrayList<Item> items = new ArrayList<>();
    public static final int MAX_SIZE = 9;
    public static final int GRID_ROW = 3;
    public static final int GRID_COL = 3;
    
    public static int inventorySelectorIndex = 0;
    public Inventory(){
    }
    public static void inventorySelectorIncrement(boolean plusone){
        if(plusone){
            if(inventorySelectorIndex>=MAX_SIZE-1){ //System.out.println("plusuoneTRUE");
                inventorySelectorIndex=0;
            }
            else{
                inventorySelectorIndex++;
            }
        }
        else{
            if(inventorySelectorIndex<=0){ //System.out.println("plusoneFALSE");
            inventorySelectorIndex=MAX_SIZE-1;
            }
            else{
                inventorySelectorIndex--;
            }
        }
    }
    public ArrayList<Item> getItems(){
        return items;   
    }  
    public int getSize(){
        return items.size();
    }
    public void setItems(ArrayList<Item> items){
        this.items = items;
    }
    public void addToInventory(Item item){
        if(items.size()<MAX_SIZE){
            items.add(item);            
        }
    }
    public void useItem(int index, Character e){
        if(!items.isEmpty()&&items.get(index).getQuantity()!=0){
            items.get(index).useItem(e);
        }
        if(items.get(index).getQuantity()<=0){
            items.remove(index);
        }
    }
    public SpawnableItem dropItem(int index, double x, double y){
        Item temp = items.get(index);
        items.remove(index);
        return new SpawnableItem(x,y,temp);
    }
    public void pickUpItem(Item item){
        if(items.size()<MAX_SIZE){
            items.add(item);            
        }
    }
    public void sortInventory(){
    for(int i=0;i<items.size();i++){
        for(int j=0;j<items.size();j++){
            if(i==j){}
            else{
                if((items.get(i).getName().equalsIgnoreCase(items.get(j).getName()))
                        &&(items.get(i).getClass().equals(items.get(j).getClass()))){
                        items.get(i).addQuantity(items.get(j).getQuantity());
                        items.remove(j);
                        j--;
                    }                    
                }
            }
        }
    }
    
    
}
