package nelsontsui.nelsonsgame.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *
 * @author Nelnel33
 */
public class Player extends Character implements Externalizable{

    public Player() {
        super();
    }

    public Player(
            double x,
            double y,
            double width,
            double height,
            double travelX,
            double travelY,
            double projectileSpeed,
            String name,
            double initHitpoints,
            double initDamage){
        super(x, y, width, height, travelX, travelY, projectileSpeed, name, initHitpoints, initDamage);
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);       
    }
    
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }
    
    @Override
    public void render(Graphics2D graphic){
        //Draw Player and Player's Projectiles
        graphic.setColor(new Color(63,131,104));//Cyanish Green
        graphic.fill(new Rectangle2D.Double(this.getX(),this.getY(),this.getWidth(),this.getHeight()));
        drawHitpointsBar(graphic,this);     
                
        if(!this.getProjectile().isEmpty()){
            for(int z=0;z<this.getProjectile().size();z++){
                this.getProjectile().get(z).render(graphic, new Color(63,131,104));
            }
        }
    }
    
}
