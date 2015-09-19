package nelsontsui.nelsonsgame.game.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class DamageableEntity extends OpaqueEntity implements Externalizable{
    
    public static final String CLASSNAME = "Damagable Entity";
    public static final String DESCRIPTION = "An solid entity that has hitpoints;Recommended Use: Physical gate/door;";
    public static final String FILENAME = "damageable_entity";
    
    protected double initHitpoints;
    protected double hitpoints;
    
    private boolean hasBeenHit;
    
    private static final long serialVersionUID = 333L;
    
    public DamageableEntity(){
        super();
    }
    public DamageableEntity(double x,
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
    
    @Override
    public String className(){
        return CLASSNAME;
    }
    
    @Override
    public String description(){
        return DESCRIPTION;
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
    
    @Override
    public void render(Graphics2D graphic){
        graphic.setColor(Color.GRAY);//greenish
        changeColorWhenHit(graphic, this);
        graphic.fill(new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
    }
    
    public void changeColorWhenHit(Graphics2D g, DamageableEntity p){
        if(p.hasBeenHit()){
            g.setColor(Color.ORANGE);
            p.setHasBeenHit(false);
        }
    }
}
