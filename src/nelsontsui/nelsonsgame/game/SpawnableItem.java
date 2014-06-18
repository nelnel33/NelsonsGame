package nelsontsui.nelsonsgame.game;

import java.util.ArrayList;

public class SpawnableItem extends Entity{
    private Item items;
    
    public static final double ITEM_WIDTH = 5;
    public static final double ITEM_HEIGHT = 5;
    
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


}
