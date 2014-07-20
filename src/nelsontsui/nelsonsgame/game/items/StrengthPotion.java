package nelsontsui.nelsonsgame.game.items;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import nelsontsui.nelsonsgame.game.Character;

public class StrengthPotion extends Potion implements Externalizable{
    
    private static final long serialVersionUID = 33148L;
    
    public StrengthPotion(){
        super();
    }
    public StrengthPotion(String name, int quantity, double strength) {
        super(name, quantity, strength);
    }
    @Override
    public void ability(Character e){
        e.setDamage(e.getDamage()+super.getStrength());
    }
    
    @Override
    public String instanceDescription(){
        return "StrengthPotion;A potion to increase a player's strength;Recommened Use:Steroids/Coffee";
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
