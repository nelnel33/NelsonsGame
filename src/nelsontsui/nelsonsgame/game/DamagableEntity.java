/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package nelsontsui.nelsonsgame.game;

/**
 *
 * @author Nelnel33
 */
public class DamagableEntity extends OpaqueEntity {
    protected double initHitpoints;
    protected double hitpoints;
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
    public void hurt(double damage){
        hitpoints-=damage;
    }
    public void heal(double heal){
        hitpoints+=heal;
    }
    
}
