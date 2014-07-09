package nelsontsui.nelsonsgame.game.items;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Weapon extends UnusableItem implements Externalizable{
    private int damage;
    private boolean equipped = false;
    
    private static final long serialVersionUID = 33144L;
    
    public Weapon(){
        super();
    }
    public Weapon(String name, int damage){
        super(name,1);
        this.damage = damage;
    } 
    public int getDamage(){
        return damage;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }
    public boolean getEquipped(){
        return equipped;
    }
    public void setEquipped(boolean e){
        equipped = e;
    }
    public String toString(){
        return super.toString()+" Damage: "+damage+";";
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        
        out.writeInt(damage);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        damage = in.readInt();
    }
}
