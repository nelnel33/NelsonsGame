package nelsontsui.nelsonsgame.game.items;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Armor extends UnusableItem implements Externalizable{
    public static final String CLASSNAME = "Armor";
    public static final String DESCRIPTION = "Armor for a player";
    public static final String FILENAME = "armor";
    
    private int protection;
    private boolean equipped=false;
    
    private static final long serialVersionUID = 33143L;
    
    public Armor(){
        super();
    }
    public Armor(String name, int protection){
        super(name,1);
        this.protection=protection;
    }
    public int getProtection(){
        return protection;
    }    
    public void setProtection(int protection){
        this.protection = protection;
    }
    public boolean getEquipped(){
        return equipped;
    }
    public void setEquipped(boolean e){
        equipped = e;
    }
    
    @Override
    public String instanceDescription(){
        return "Armor;Protection for the player, increases a players health by x amount;Recommened Use: Armor;";
    }
    
    @Override
    public String toString(){
        return super.toString()+" Protection: "+protection+";";
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
              
        out.writeInt(protection);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        protection = in.readInt();        
    }
}
