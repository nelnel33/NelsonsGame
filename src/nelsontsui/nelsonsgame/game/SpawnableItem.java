package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import nelsontsui.nelsonsgame.game.items.Item;

public class SpawnableItem extends Entity implements Externalizable{
    
    public static final String CLASSNAME = "SpawnableItem";    
    public static final String DESCRIPTION = "A non-solid object that contains";
    
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
    
    @Override
    public String className(){
        return CLASSNAME;
    }
    
    @Override
    public String description(){
        return DESCRIPTION;
    }
    
    public String instanceDescription(){
        return DESCRIPTION+items.instanceDescription();
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
    
    @Override
    public void render(Graphics2D graphic){
        graphic.setColor(new Color(188,101,121));
        graphic.fill(new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
    }
}
