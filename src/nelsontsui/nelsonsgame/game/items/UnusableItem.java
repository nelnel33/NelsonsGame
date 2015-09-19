package nelsontsui.nelsonsgame.game.items;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class UnusableItem extends Item{
    
    public static final String CLASSNAME = "Unusable Item";
    public static final String DESCRIPTION = "An item that cannot be used(eaten/drank) but can be wield or equipped";
    public static final String FILENAME = "unusable_item";
    
    private static final long serialVersionUID = 33142L;
    
    public UnusableItem(){
        super();
    }    
    public UnusableItem(String name, int quantity) {
        super(name, quantity);
    }
    
    @Override
    public String instanceDescription(){
        return "UnusableItem;A generic, non-perishable item;Recommened Use: Armor/Weapons";
    }
    @Override
    public String toString(){
        return super.toString();
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }
    
}
