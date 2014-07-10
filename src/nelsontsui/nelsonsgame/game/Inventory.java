package nelsontsui.nelsonsgame.game;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import nelsontsui.nelsonsgame.game.items.Item;

public class Inventory implements Externalizable{
    protected ArrayList<Item> items = new ArrayList<>();
    
    public static final int MAX_SIZE = 9;
    public static final int GRID_ROW = 3;
    public static final int GRID_COL = 3;
    
    public static int inventorySelectorIndex = 0;
    
    private static final long serialVersionUID = 351L;
    
    public Inventory(){}
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
                if(items.get(i) == null || items.get(i).getName() == null){items.remove(i);}
                else if(items.get(j) == null || items.get(j).getName() == null){items.remove(j);}
                else if((items.get(i).getName().equalsIgnoreCase(items.get(j).getName()))
                        && (items.get(i).getClass().equals(items.get(j).getClass()))){
                        items.get(i).addQuantity(items.get(j).getQuantity());
                        items.remove(j);
                        j--;
                    }                    
                }
            }
        }
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(items.size());
        for(int i=0;i<items.size();i++){
            out.writeObject(items.get(i));
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int itemsize = in.readInt();
        for(int i=0;i<itemsize;i++){
            items.add((Item)in.readObject());
        }
    }
    
    
}
