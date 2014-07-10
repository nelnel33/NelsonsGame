package nelsontsui.nelsonsgame.game.items;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import nelsontsui.nelsonsgame.game.items.Item;

public class Potion extends Item implements Externalizable{
    private double strength;
    
    private static final long serialVersionUID = 33147L;
    
    public Potion(){
        super();
    }
    public Potion(String name,
                  int quantity,
                  double strength){
        super(name,quantity);
        this.strength = strength;
    }
    public double getStrength(){
        return strength;
    }
    public void setStrength(double strength){
        this.strength = strength;
    }
    public String toString(){
        return "PotionStrength: "+strength+"; "+super.toString();
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        
        out.writeDouble(strength);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        strength = in.readDouble();
    }

    
}
