package nelsontsui.nelsonsgame.game;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DamagableEntity extends OpaqueEntity implements Externalizable{
    protected double initHitpoints;
    protected double hitpoints;
    
    private boolean hasBeenHit;
    
    private static final long serialVersionUID = 333L;
    
    public DamagableEntity(){
        super();
    }
    public DamagableEntity(double x,
                           double y,
                           double width,
                           double height,
                           double initHitpoints) {
        super(x, y, width, height);
        hitpoints = initHitpoints;
    }
    public double getinitHitpoints(){
        return initHitpoints;
    }
    public double getHitpoints(){
        return hitpoints;
    }
    public void setHitpoints(double hitpoints){
        this.hitpoints = hitpoints;
    }
    public boolean hasBeenHit(){
        return hasBeenHit;
    }
    public void setHasBeenHit(boolean hasBeenHit){
        this.hasBeenHit = hasBeenHit;
    }
    public void hurt(double damage){
        hitpoints-=damage;
    }
    public void heal(double heal){
        hitpoints+=heal;
    }
    
    public static String description(){
        return "DamagableEntity;An solid entity that has hitpoints;Recommended Use: Physical gate/door;";
    }
    
    @Override
    public String toString(){
        return super.toString()+" initHitpoints: "+initHitpoints+"; hitpoints: "+hitpoints+";";
    }    
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        
        out.writeDouble(initHitpoints);
        out.writeDouble(hitpoints);
        
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        
        initHitpoints = in.readDouble();
        hitpoints = in.readDouble();
    }
}
