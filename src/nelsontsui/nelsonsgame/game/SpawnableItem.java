package nelsontsui.nelsonsgame.game;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import nelsontsui.nelsonsgame.game.items.Item;

public class SpawnableItem extends Entity implements Externalizable{
    protected Item items;
    
    public static final double ITEM_WIDTH = 5;
    public static final double ITEM_HEIGHT = 5;
    
    private static final long serialVersionUID = 3314L;
    
    public SpawnableItem(){
        super();
    }
    
    public SpawnableItem(double x, double y, Item items) {
        super(x, y, ITEM_WIDTH, ITEM_HEIGHT);
        this.items = items;
    }
    public Item getItems(){
        return items;
    }
    public void setItems(Item items){
        this.items = items;
    }
    
    public static String description(){
        return "SpawnableItem;A non-solid object that contains an item;";
    }
    
    public String instanceDescription(){
        return "SpawnableItem;A non-solid object that contains;"+items.instanceDescription();
    }
    
    @Override
    public String toString(){
        return items.toString()+" "+super.toString();
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);   
                
        out.writeObject(items);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        items = (Item)in.readObject();
    }
}
